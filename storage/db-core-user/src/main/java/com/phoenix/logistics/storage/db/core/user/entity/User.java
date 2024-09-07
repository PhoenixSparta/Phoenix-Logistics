package com.phoenix.logistics.storage.db.core.user.entity;

import com.phoenix.logistics.core.enums.RoleType;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "p_users")
public class User extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long userId; // 사용자 ID, Primary Key

    @Column(name = "username", length = 10, nullable = false)
    private String username; // 사용자 닉네임, NOT NULL, 4자 이상 10자 이하, 알파벳 소문자(a-z), 숫자(0-9)

    @Column(name = "password", length = 15, nullable = false)
    private String password; // 사용자 비밀번호, 8자 이상 15자 이하, 알파벳 대소문자, 숫자, 특수문자 포함

    @Enumerated(EnumType.STRING)
    @Column(name = "role_type", nullable = false)
    private RoleType role; // 사용자 역할 (MASTER_ADMIN, HUB_ADMIN, HUB_DELIVERY_AGENT,
                           // HUB_VENDOR, HUB_MANUFACTUR)

    @Column(name = "is_public", nullable = false)
    private Boolean isPublic = true; // 계정 활성화 상태, TRUE이면 활성, FALSE이면 비활성

    @Column(name = "is_delete", nullable = false)
    private Boolean isDelete = false; // 논리적 삭제 여부, 기본값 FALSE

    @Builder
    public User(String username, String password, RoleType role) {
        this.username = username;
        this.password = password;
        this.role = role;
        this.isPublic = true;
        this.isDelete = false;
    }

}
