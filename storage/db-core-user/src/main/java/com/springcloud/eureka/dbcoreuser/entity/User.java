package com.springcloud.eureka.dbcoreuser.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "p_users")
public class User {

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

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt; // 레코드 생성 시간

    @Column(name = "created_by")
    private Long createdBy; // 레코드 생성자 (userId)

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt; // 레코드 수정 시간

    @Column(name = "updated_by", length = 100)
    private Long updatedBy; // 레코드 수정자 (userId)

    @Column(name = "deleted_at")
    private LocalDateTime deletedAt; // 레코드 삭제 시간

    @Column(name = "deleted_by", length = 100)
    private Long deletedBy; // 레코드 삭제자 (userId)

    @Builder
    public User(String username, String password, RoleType role) {
        this.username = username;
        this.password = password;
        this.role = role;
        this.isPublic = true;
        this.isDelete = false;
    }

}
