package com.phoenix.logistics.core.auth.api.controller;

import com.phoenix.logistics.core.auth.api.config.jwt.JwtUtil;
import com.phoenix.logistics.core.auth.api.controller.dto.request.UserLoginRequest;
import com.phoenix.logistics.core.auth.api.controller.dto.request.UserSignupRequest;
import com.phoenix.logistics.core.auth.domain.UserService;
import com.phoenix.logistics.storage.db.core.user.entity.User;
import jakarta.servlet.http.HttpServletResponse;
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

    private final JwtUtil jwtUtil;

    // 회원가입
    @PostMapping("/signup")
    public ResponseEntity<String> signup(@RequestBody @Valid UserSignupRequest request) {
        return ResponseEntity.ok(userService.signup(request) + " registered successfully");
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody @Valid UserLoginRequest request, HttpServletResponse response)
            throws IllegalAccessException {

        User loginedUser = userService.login(request);
        String token = jwtUtil.createToken(loginedUser.getUserId(), loginedUser.getUsername(), loginedUser.getRole());
        response.setHeader(JwtUtil.AUTHORIZATION_HEADER, token);

        return ResponseEntity.ok().body(loginedUser.getRole().toString());
    }

}