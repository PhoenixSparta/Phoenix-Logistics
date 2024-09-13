package com.phoenix.logistics.core.auth;

import com.phoenix.logistics.core.auth.api.config.jwt.JwtUtil;
import com.phoenix.logistics.core.auth.api.controller.AuthController;
import io.jsonwebtoken.Claims;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class AuthControllerTest {

    @Mock
    private JwtUtil jwtUtil;

    @InjectMocks
    private AuthController authController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void validateToken_ValidToken_ReturnsClaims() {
        // Arrange
        String token = "Bearer valid-token";
        Claims mockClaims = mock(Claims.class);
        when(jwtUtil.extractClaims(anyString())).thenReturn(mockClaims);

        // Act
        ResponseEntity<Claims> response = authController.validateToken(token);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(mockClaims, response.getBody());
        verify(jwtUtil, times(1)).extractClaims("valid-token");
    }

    @Test
    void validateToken_InvalidToken_ReturnsUnauthorized() {
        // Arrange
        String token = "Bearer invalid-token";
        when(jwtUtil.extractClaims(anyString())).thenThrow(new RuntimeException("Invalid Token"));

        // Act
        ResponseEntity<Claims> response = authController.validateToken(token);

        // Assert
        assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
        verify(jwtUtil, times(1)).extractClaims("invalid-token");
    }

}
