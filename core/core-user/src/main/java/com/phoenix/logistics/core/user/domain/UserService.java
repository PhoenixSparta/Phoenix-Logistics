package com.phoenix.logistics.core.user.domain;

import com.phoenix.logistics.core.enums.RoleType;

import com.phoenix.logistics.core.user.api.controller.dto.UserRequestDto;
import com.phoenix.logistics.core.user.api.controller.dto.UserResponseDto;
import com.phoenix.logistics.storage.db.core.user.entity.User;
import com.phoenix.logistics.storage.db.core.user.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    // 회원가입 비즈니스 로직 처리 (이미 암호화된 비밀번호를 저장)
    public UserResponseDto createUser(UserRequestDto request) {
        // 암호화된 비밀번호를 그대로 사용
        User user = new User(request.username(), request.password(), RoleType.USER);
        userRepository.save(user);

        // 응답 객체로 변환하여 반환
        return new UserResponseDto(user.getUserId(), user.getUsername(), user.getPassword(), user.getRole());
    }

    public String getUserInfo(Long userId) {
        User user = getUserFromId(userId);
        return "User ID: " + user.getUserId() + ", Username: " + user.getUsername() + ", Role: " + user.getRole();

    }

    // 사용자명으로 사용자 조회
    public UserResponseDto findUserByUsername(String username) {
        // 사용자 조회
        User user = userRepository.findByUsername(username)
            .orElseThrow(() -> new IllegalArgumentException("User not found"));

        // 응답 객체로 변환하여 반환 (암호화된 비밀번호 포함)
        return new UserResponseDto(user.getUserId(), user.getUsername(), user.getPassword(), user.getRole());
    }

    public boolean existsByUsername(String username) {
        return userRepository.existsByUsername(username);
    }

    @Transactional
    public String deleteUser(Long userId) {
        User user = getUserFromId(userId);
        user.softDelete();
        return "User ID: " + user.getUserId() + " Soft Delete is complete";
    }

    private User getUserFromId(Long userId) {
        return userRepository.findByUserIdAndIsDeleteFalse(userId)
            .orElseThrow(() -> new IllegalArgumentException("Not Found User or User is Deleted"));
    }

}
