package com.phoenix.logistics.core.user;

import com.phoenix.logistics.core.enums.RoleType;
import com.phoenix.logistics.core.user.api.config.jwt.CustomUserDetails;

import com.phoenix.logistics.core.user.domain.UserService;
import com.phoenix.logistics.storage.db.core.user.entity.User;
import com.phoenix.logistics.storage.db.core.user.repository.UserRepository;
import java.util.Optional;
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

    @InjectMocks
    private UserService userService;

    @BeforeEach
    void setUp() {
        // Mockito 초기화
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void 유저정보_조회_성공() {
        // given: Mock된 CustomUserDetails 설정
        CustomUserDetails userDetails = new CustomUserDetails(1L, "testUser", RoleType.USER);

        // given: 유저 엔티티 설정 (userId는 자동 생성되므로 설정하지 않음)
        User user = User.builder().username("testUser").role(RoleType.USER).build();

        // when: findUserByUsername이 호출되면 유저 반환
        when(userRepository.findByUsername("testUser")).thenReturn(Optional.ofNullable(user));

        // when: 서비스 호출
        String userInfo = userService.getUserInfo(userDetails);

        // then: 결과 검증
        assertEquals("User ID: " + user.getUserId() + ", Username: testUser, Role: USER", userInfo);

        // findUserByUsername이 호출되었는지 확인
        verify(userRepository, times(1)).findByUsername("testUser");
    }

    @Test
    void 유저정보_조회_실패_존재하지_않는_유저() {
        // given: Mock된 CustomUserDetails 설정
        CustomUserDetails userDetails = new CustomUserDetails(1L, "unknownUser", RoleType.USER);

        // when: findByUsername이 호출되면 Optional.empty() 반환
        when(userRepository.findByUsername("unknownUser")).thenReturn(Optional.empty());

        // then: 유저가 없을 때 예외 발생 확인
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            userService.getUserInfo(userDetails);
        });

        assertEquals("User not found", exception.getMessage());

        // findByUsername이 호출되었는지 확인
        verify(userRepository, times(1)).findByUsername("unknownUser");
    }

}
