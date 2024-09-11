package com.phoenix.logistics.core.user.domain;

import com.phoenix.logistics.core.enums.RoleType;
import com.phoenix.logistics.core.user.api.config.jwt.CustomUserDetails;

import com.phoenix.logistics.storage.db.core.user.entity.User;
import com.phoenix.logistics.storage.db.core.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public String getUserInfo(CustomUserDetails userDetails) {
        User user = findUserByUsername(userDetails.getUsername());
        return "User ID: " + user.getUserId() + ", Username: " + user.getUsername() + ", Role: " + user.getRole();

    }

    private User findUserByUsername(String username) {
        return userRepository.findByUsername(username)
            .orElseThrow(() -> new IllegalArgumentException("User not found"));
    }



}
