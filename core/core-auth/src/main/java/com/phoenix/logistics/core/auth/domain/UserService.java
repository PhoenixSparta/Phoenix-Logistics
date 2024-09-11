package com.phoenix.logistics.core.auth.domain;

import com.phoenix.logistics.core.auth.api.controller.dto.request.UserLoginRequest;
import com.phoenix.logistics.core.auth.api.controller.dto.request.UserSignupRequest;
import com.phoenix.logistics.core.enums.RoleType;

import com.phoenix.logistics.storage.db.core.user.entity.User;
import com.phoenix.logistics.storage.db.core.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    public String signup(UserSignupRequest request) {
        validateUsername(request.getUsername());
        String encodedPassword = encodePassword(request.getPassword());
        User user = createUser(request.getUsername(), encodedPassword);
        saveUser(user);
        return user.getUsername();
    }

    public User login(UserLoginRequest request) throws IllegalAccessException {
        User user = findUserByUsername(request.getUsername());
        verifyPassword(request.getPassword(), user.getPassword());
        return user;
    }

    // 사용자명 중복 확인 메소드
    private void validateUsername(String username) {
        if (userRepository.existsByUsername(username)) {
            throw new IllegalArgumentException("Username already exists.");
        }
    }

    // 비밀번호 암호화 메소드
    private String encodePassword(String password) {
        return passwordEncoder.encode(password);
    }

    // User 엔티티 생성 메소드
    private User createUser(String username, String encodedPassword) {
        return User.builder().username(username).password(encodedPassword).role(RoleType.USER).build();
    }

    // 데이터베이스에 사용자 저장 메소드
    private void saveUser(User user) {
        userRepository.save(user);
    }

    private User findUserByUsername(String username) {
        return userRepository.findByUsername(username)
            .orElseThrow(() -> new IllegalArgumentException("User not found"));
    }

    private void verifyPassword(String rawPassword, String encodedPassword) throws IllegalAccessException {
        if (!passwordEncoder.matches(rawPassword, encodedPassword)) {
            throw new IllegalAccessException("비밀번호가 일치하지 않습니다.");
        }
    }

}
