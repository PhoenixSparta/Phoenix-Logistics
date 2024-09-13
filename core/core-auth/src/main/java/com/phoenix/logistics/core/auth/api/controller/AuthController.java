package com.phoenix.logistics.core.auth.api.controller;

import com.phoenix.logistics.core.auth.api.config.jwt.JwtUtil;
import com.phoenix.logistics.core.auth.api.controller.dto.request.UserLoginRequest;
import com.phoenix.logistics.core.auth.api.controller.dto.request.UserSignupRequest;
import com.phoenix.logistics.core.auth.domain.UserService;
import com.phoenix.logistics.storage.db.core.user.entity.User;
import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
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

    // 로그인
    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody @Valid UserLoginRequest request, HttpServletResponse response)
            throws IllegalAccessException {

        User loginedUser = userService.login(request);
        String token = jwtUtil.createToken(loginedUser.getUserId(), loginedUser.getUsername(), loginedUser.getRole());
        response.setHeader(JwtUtil.AUTHORIZATION_HEADER, token);

        return ResponseEntity.ok().body(loginedUser.getRole().toString());
    }

    // 토큰 검증
    @PostMapping("/validate-token")
    public ResponseEntity<Claims> validateToken(@RequestHeader("Authorization") String token) {
        try {
            // "Bearer " 부분 제거
            String jwtToken = token.substring(7);

            // JWT 토큰을 검증하고 Claims 반환
            Claims claims = jwtUtil.extractClaims(jwtToken);

            // 검증된 Claims 반환
            return ResponseEntity.ok(claims);
        }
        catch (Exception e) {
            // 검증 실패 시 401 Unauthorized 반환
            return ResponseEntity.status(401).build();
        }
    }

}