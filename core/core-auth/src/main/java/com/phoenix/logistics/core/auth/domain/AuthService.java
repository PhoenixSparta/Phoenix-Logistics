package com.phoenix.logistics.core.auth.domain;

import com.phoenix.logistics.client.user.UserClient;
import com.phoenix.logistics.client.user.model.UserClientResult;
import com.phoenix.logistics.core.auth.api.controller.dto.request.UserLoginRequest;
import com.phoenix.logistics.core.auth.api.controller.dto.request.UserSignupRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserClient userClient;

    private final PasswordEncoder passwordEncoder;

    public String signup(UserSignupRequest request) {
        validateUsername(request.getUsername());
        String encodedPassword = passwordEncoder.encode(request.getPassword());
        userClient.signup(request.getUsername(), encodedPassword);
        return request.getUsername();
    }

    public UserClientResult login(UserLoginRequest request) throws IllegalAccessException {
        UserClientResult userModel = userClient.findUserByUsername(request.getUsername());
        verifyPassword(request.getPassword(), userModel.password());
        return userModel;
    }

    private void validateUsername(String username) {
        if (userClient.existsByUsername(username)) {
            throw new IllegalArgumentException("Username already exists.");
        }
    }

    private void verifyPassword(String rawPassword, String encodedPassword) throws IllegalAccessException {
        if (!passwordEncoder.matches(rawPassword, encodedPassword)) {
            throw new IllegalAccessException("비밀번호가 일치하지 않습니다.");
        }
    }

}
