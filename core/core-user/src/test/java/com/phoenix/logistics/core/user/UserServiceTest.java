package com.phoenix.logistics.core.user;

import com.phoenix.logistics.core.enums.RoleType;
import com.phoenix.logistics.core.user.api.controller.dto.request.UserSignupRequest;
import com.phoenix.logistics.core.user.domain.UserService;
import com.phoenix.logistics.storage.db.core.user.entity.User;
import com.phoenix.logistics.storage.db.core.user.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserService userService;

    @BeforeEach
    void setUp() {
        // Mockito 초기화
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void 회원가입_실패_이미_존재하는_사용자명() {
        // given: 이미 존재하는 사용자 이름을 설정
        UserSignupRequest request = new UserSignupRequest();
        request.setUsername("existingUser");
        request.setPassword("password123");

        // when: 존재하는 사용자가 있을 때
        when(userRepository.existsByUsername("existingUser")).thenReturn(true);

        // then: 예외가 발생하는지 확인
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            userService.signup(request);
        });

        assertEquals("Username already exists.", exception.getMessage());

        // validateUsername 호출 검증
        verify(userRepository, times(1)).existsByUsername("existingUser");
        // saveUser는 호출되지 않아야 함
        verify(userRepository, never()).save(any(User.class));
    }

    @Test
    void 회원가입_성공_존재하지_않는_사용자명() {
        // given: 새로운 사용자 이름과 비밀번호 설정
        UserSignupRequest request = new UserSignupRequest();
        request.setUsername("newUser");
        request.setPassword("password123");

        // when: 사용자 이름이 존재하지 않음
        when(userRepository.existsByUsername("newUser")).thenReturn(false);
        when(passwordEncoder.encode("password123")).thenReturn("encodedPassword123");

        // 사용자가 저장될 때의 가상 반환값
        User savedUser = User.builder().username("newUser").password("encodedPassword123").role(RoleType.USER).build();

        // 저장 시 가짜 응답
        when(userRepository.save(any(User.class))).thenReturn(savedUser);

        // when: 서비스 호출
        String username = userService.signup(request);

        // then: 결과 검증
        assertEquals("newUser", username);

        // 각 메소드 호출 검증
        verify(userRepository, times(1)).existsByUsername("newUser");
        verify(passwordEncoder, times(1)).encode("password123");
        verify(userRepository, times(1)).save(any(User.class));
    }

}
