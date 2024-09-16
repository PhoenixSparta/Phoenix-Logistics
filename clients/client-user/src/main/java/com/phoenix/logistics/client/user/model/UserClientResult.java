package com.phoenix.logistics.client.user.model;

import com.phoenix.logistics.core.enums.RoleType;

public record UserClientResult(Long id, String username, String password, RoleType role) {

}
