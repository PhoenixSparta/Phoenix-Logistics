package com.phoenix.logistics.core.user.api.controller;

import com.phoenix.logistics.core.user.api.controller.dto.UserRequestDto;
import com.phoenix.logistics.core.user.api.controller.dto.UserResponseDto;
import com.phoenix.logistics.core.user.domain.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("feign/users")
public class ClientController {

    private final UserService userService;

    @PostMapping
    public ResponseEntity<UserResponseDto> createUser(@RequestBody UserRequestDto request) {
        UserResponseDto userResponse = userService.createUser(request);
        return ResponseEntity.ok(userResponse);
    }

    // 사용자명으로 사용자 조회
    @GetMapping("/{username}")
    public ResponseEntity<UserResponseDto> findUserByUsername(@PathVariable(name = "username") String username) {
        UserResponseDto userResponse = userService.findUserByUsername(username);
        return ResponseEntity.ok(userResponse);
    }

    @GetMapping("/exist/{username}")
    public ResponseEntity<Boolean> existsByUsername(@PathVariable String username) {
        boolean exists = userService.existsByUsername(username);
        return ResponseEntity.ok(exists);
    }

}
