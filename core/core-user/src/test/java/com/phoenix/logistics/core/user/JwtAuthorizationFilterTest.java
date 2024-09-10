package com.phoenix.logistics.core.user;

import com.phoenix.logistics.core.user.api.config.jwt.JwtAuthorizationFilter;
import com.phoenix.logistics.core.user.api.config.jwt.JwtUtil;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;

import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.lang.reflect.Method;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class JwtAuthorizationFilterTest {

    @Mock
    private JwtUtil jwtUtil;

    @Mock
    private UserDetailsService userDetailsService;

    @Mock
    private FilterChain filterChain;

    private JwtAuthorizationFilter jwtAuthorizationFilter;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        jwtAuthorizationFilter = new JwtAuthorizationFilter(jwtUtil);
    }

    @Test
    void 유효한_토큰_인증설정() throws Exception {
        // Mock valid JWT token
        String token = "validToken";
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.addHeader("Authorization", "Bearer " + token);
        MockHttpServletResponse response = new MockHttpServletResponse();

        // Mock JWT claims
        Claims claims = Jwts.claims().setSubject("12345");
        claims.put("username", "testUser");
        claims.put("role", "USER");

        when(jwtUtil.resolveToken(request)).thenReturn(token);
        when(jwtUtil.isTokenValid(token)).thenReturn(true);
        when(jwtUtil.getUserInfoFromToken(token)).thenReturn(claims);

        // 리플렉션을 사용해 protected 메소드 호출
        Method method = JwtAuthorizationFilter.class.getDeclaredMethod("doFilterInternal", HttpServletRequest.class,
                HttpServletResponse.class, FilterChain.class);
        method.setAccessible(true); // 메소드 접근 가능하게 설정

        // 메소드 호출
        method.invoke(jwtAuthorizationFilter, request, response, filterChain);

        // 검증
        assertNotNull(SecurityContextHolder.getContext().getAuthentication());
        assertEquals("testUser", SecurityContextHolder.getContext().getAuthentication().getName());
        verify(filterChain, times(1)).doFilter(request, response);
    }

    @Test
    void 유효하지않은_토큰_인증미설정() throws Exception {
        String token = "invalidToken";
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.addHeader("Authorization", "Bearer " + token);
        MockHttpServletResponse response = new MockHttpServletResponse();

        when(jwtUtil.resolveToken(request)).thenReturn(token);
        when(jwtUtil.isTokenValid(token)).thenReturn(false);

        // 리플렉션을 사용해 protected 메소드 호출
        Method method = JwtAuthorizationFilter.class.getDeclaredMethod("doFilterInternal", HttpServletRequest.class,
                HttpServletResponse.class, FilterChain.class);
        method.setAccessible(true); // 메소드 접근 가능하게 설정

        // 메소드 호출
        method.invoke(jwtAuthorizationFilter, request, response, filterChain);

        // 검증
        assertNull(SecurityContextHolder.getContext().getAuthentication());
        verify(filterChain, times(1)).doFilter(request, response);
    }

}
