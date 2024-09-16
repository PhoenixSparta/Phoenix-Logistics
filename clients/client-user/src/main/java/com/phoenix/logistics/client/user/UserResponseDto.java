package com.phoenix.logistics.client.user;

import com.phoenix.logistics.core.enums.RoleType;

public record UserResponseDto(Long userId, String username, String password, RoleType role) {

}
