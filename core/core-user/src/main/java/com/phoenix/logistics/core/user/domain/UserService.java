package com.phoenix.logistics.core.user.domain;

import com.phoenix.logistics.storage.db.core.user.entity.User;
import com.phoenix.logistics.storage.db.core.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public String getUserInfo(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new IllegalArgumentException("Not Found User"));
        return "User ID: " + user.getUserId() + ", Username: " + user.getUsername() + ", Role: " + user.getRole();

    }

}
