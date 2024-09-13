package com.phoenix.logistics.core.auth.api.controller;

import com.phoenix.logistics.core.auth.api.config.jwt.JwtUtil;
import com.phoenix.logistics.core.auth.api.controller.dto.request.UserLoginRequest;
import com.phoenix.logistics.core.auth.api.controller.dto.request.UserSignupRequest;
import com.phoenix.logistics.core.auth.domain.UserService;
import com.phoenix.logistics.storage.db.core.user.entity.User;
import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import java.util.HashMap;
import java.util.Map;
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
    public ResponseEntity<Map<String, Object>> validateToken(@RequestHeader("Authorization") String token) {
        try {
            // "Bearer " 부분 제거
            String jwtToken = token.substring(7);

            // JWT 토큰을 검증하고 Claims 반환
            Claims claims = jwtUtil.extractClaims(jwtToken);

            // Claims 내용을 Map으로 변환하여 반환
            Map<String, Object> response = new HashMap<>();
            response.put("sub", claims.getSubject());
            response.put("username", claims.get("username"));
            response.put("role", claims.get("role"));
            response.put("exp", claims.getExpiration());
            response.put("iat", claims.getIssuedAt());

            // 검증된 Claims 반환
            return ResponseEntity.ok(response);
        }
        catch (Exception e) {
            // 검증 실패 시 401 Unauthorized 반환
            return ResponseEntity.status(401).build();
        }
    }

}