package com.phoenix.logistics.support.authentication.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.hierarchicalroles.RoleHierarchy;
import org.springframework.security.access.hierarchicalroles.RoleHierarchyImpl;

@Configuration
public class RoleHierarchyConfig {

    @Bean
    public RoleHierarchy roleHierarchy() {
        RoleHierarchyImpl roleHierarchy = new RoleHierarchyImpl();

        // 상하 관계 정의
        String hierarchy = "ROLE_MASTER_ADMIN > ROLE_HUB_MANAGER \n" + "ROLE_HUB_MANAGER > ROLE_DELIVERY_STAFF \n"
                + "ROLE_HUB_MANAGER > ROLE_VENDOR \n" + "ROLE_HUB_MANAGER > ROLE_MANUFACTURER \n"
                + "ROLE_VENDOR > ROLE_USER \n" + "ROLE_MANUFACTURER > ROLE_USER";

        roleHierarchy.setHierarchy(hierarchy);
        return roleHierarchy;
    }

}
