package com.phoenix.logistics.core.user.service;

import com.phoenix.logistics.core.enums.RoleType;
import com.phoenix.logistics.core.user.dto.request.UserSignupRequest;
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
        // 비밀번호 암호화
        String encodedPassword = passwordEncoder.encode(request.getPassword());

        // User 엔티티 생성
        User user = User.builder()
            .username(request.getUsername())
            .password(encodedPassword)
            .role(RoleType.USER)
            .build();

        // 데이터베이스에 저장
        userRepository.save(user);

        return user.getUsername();
    }

}
