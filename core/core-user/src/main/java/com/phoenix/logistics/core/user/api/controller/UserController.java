package com.phoenix.logistics.core.user.api.controller;

import com.phoenix.logistics.core.user.domain.UserService;
import com.phoenix.logistics.support.authentication.security.SecurityUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PreAuthorize("hasRole('USER')")
    @GetMapping
    public String getUserInfo() {
        Long userId = SecurityUtil.getCurrentUserId();
        return userService.getUserInfo(userId);
    }

}
