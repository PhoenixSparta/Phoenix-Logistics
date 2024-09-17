package com.phoenix.logistics.core.user.api.controller.dto;

import com.phoenix.logistics.core.enums.RoleType;

public record UserResponseDto(Long userId, String username, String password, RoleType role) {

}
