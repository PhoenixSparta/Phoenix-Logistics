package com.phoenix.logistics.core.user.domain;

import com.phoenix.logistics.core.enums.RoleType;
import com.phoenix.logistics.storage.db.core.user.entity.User;
import com.phoenix.logistics.storage.db.core.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class AdminAccountInitializer {

    private final UserRepository userRepository;

    @Value("${admin.default.password}")
    private String adminPassword;

    public AdminAccountInitializer(UserRepository userRepository) {
        this.userRepository = userRepository;

    }

    @Bean
    public CommandLineRunner initAdminAccount() {
        return args -> {
            // admin 계정이 이미 있는지 확인

            // 기본 admin 계정 생성
            User admin = User.builder()
                .username("admin")
                .password(adminPassword) // 초기 비밀번호 설정
                .role(RoleType.MASTER_ADMIN) // 최고 권한 부여
                .build();

            userRepository.save(admin);
            System.out.println("Admin 계정이 생성되었습니다.");
        };
    }

}
