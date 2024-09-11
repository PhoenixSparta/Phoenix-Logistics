package com.phoenix.logistics.core.auth;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.anyString;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.phoenix.logistics.core.auth.api.controller.dto.request.UserLoginRequest;
import com.phoenix.logistics.core.auth.api.controller.dto.request.UserSignupRequest;
import com.phoenix.logistics.core.auth.domain.UserService;
import com.phoenix.logistics.core.enums.RoleType;

import com.phoenix.logistics.storage.db.core.user.entity.User;
import com.phoenix.logistics.storage.db.core.user.repository.UserRepository;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.password.PasswordEncoder;

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

    @Test
    void 로그인_성공() throws IllegalAccessException {
        // given: 요청 DTO와 데이터베이스에 있는 사용자 설정
        UserLoginRequest request = new UserLoginRequest();
        request.setUsername("testUser");
        request.setPassword("password123");

        // 사용자 엔티티의 비밀번호는 암호화된 값이어야 함
        User user = User.builder().username("testUser").password("encodedPassword").role(RoleType.USER).build();

        // when: 사용자 이름으로 사용자 찾기
        when(userRepository.findByUsername("testUser")).thenReturn(Optional.of(user));
        // passwordEncoder.matches() 호출 시 rawPassword와 encodedPassword 비교
        when(passwordEncoder.matches("password123", "encodedPassword")).thenReturn(true);

        // then: 로그인이 성공했는지 확인
        User loginedUser = userService.login(request);
        assertNotNull(loginedUser);
        assertEquals("testUser", loginedUser.getUsername());

        verify(userRepository, times(1)).findByUsername("testUser");
        verify(passwordEncoder, times(1)).matches("password123", "encodedPassword");
    }

    @Test
    void 로그인_실패_존재하지_않는_사용자() {
        // given: 존재하지 않는 사용자 이름 설정
        UserLoginRequest request = new UserLoginRequest();
        request.setUsername("unknownUser");
        request.setPassword("password123");

        // when: 사용자 이름으로 찾지 못할 때
        when(userRepository.findByUsername("unknownUser")).thenReturn(Optional.empty());

        // then: IllegalArgumentException 발생 확인
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            userService.login(request);
        });
        assertEquals("User not found", exception.getMessage());

        verify(userRepository, times(1)).findByUsername("unknownUser");
        verify(passwordEncoder, never()).matches(anyString(), anyString()); // 비밀번호 검증이
                                                                            // 실행되지 않음
    }

    @Test
    void 로그인_실패_비밀번호_불일치() {
        // given: 요청 DTO와 데이터베이스에 있는 사용자 설정
        UserLoginRequest request = new UserLoginRequest();
        request.setUsername("testUser");
        request.setPassword("wrongPassword");

        // 실제 암호화된 비밀번호를 가정
        User user = User.builder().username("testUser").password("encodedPassword").role(RoleType.USER).build();

        // when: 사용자 이름으로 사용자 찾기
        when(userRepository.findByUsername("testUser")).thenReturn(Optional.of(user));
        // 잘못된 비밀번호로 암호화된 비밀번호와 비교
        when(passwordEncoder.matches("wrongPassword", "encodedPassword")).thenReturn(false);

        // then: IllegalAccessException 발생 확인
        IllegalAccessException exception = assertThrows(IllegalAccessException.class, () -> {
            userService.login(request);
        });
        assertEquals("비밀번호가 일치하지 않습니다.", exception.getMessage());

        verify(userRepository, times(1)).findByUsername("testUser");
        verify(passwordEncoder, times(1)).matches("wrongPassword", "encodedPassword");
    }

}
