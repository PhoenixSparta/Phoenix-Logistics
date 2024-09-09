package com.phoenix.logistics.core.user.controller;

import com.phoenix.logistics.core.user.dto.request.UserSignupRequest;
import com.phoenix.logistics.core.user.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    // 추후 Auth 서비스로 분리

    private final UserService userService;

    // 회원가입
    @PostMapping("/signup")
    public ResponseEntity<String> signup(@RequestBody @Valid UserSignupRequest request) {
        return ResponseEntity.ok(userService.signup(request) + " registered successfully");
    }

}