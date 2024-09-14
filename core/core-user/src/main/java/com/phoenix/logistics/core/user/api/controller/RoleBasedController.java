package com.phoenix.logistics.core.user.api.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RoleBasedController {

    /*
     * 권한 사용 방법 예시 코드입니다.
     *
     * ROLE_MASTER_ADMIN > ROLE_HUB_MANAGER
     *
     * ROLE_HUB_MANAGER > ROLE_DELIVERY_STAFF
     *
     * ROLE_HUB_MANAGER > ROLE_VENDOR
     *
     * ROLE_HUB_MANAGER > ROLE_MANUFACTURER
     *
     * ROLE_VENDOR > ROLE_USER
     *
     * ROLE_MANUFACTURER > ROLE_USER
     */

    @PreAuthorize("hasRole('MASTER_ADMIN')")
    @GetMapping("/admin/manage")
    public String manageSystem() {
        return "시스템 관리 페이지 (MASTER_ADMIN)";
    }

    @PreAuthorize("hasRole('HUB_MANAGER')")
    @GetMapping("/hub/manage")
    public String manageHub() {
        return "허브 관리 페이지 (HUB_MANAGER)";
    }

    @PreAuthorize("hasRole('DELIVERY_STAFF')")
    @GetMapping("/delivery/manage")
    public String manageDelivery() {
        return "배송 정보 관리 페이지 (DELIVERY_STAFF)";
    }

    @PreAuthorize("hasRole('VENDOR')")
    @GetMapping("/vendor/manage")
    public String manageVendor() {
        return "업체 관리 페이지 (VENDOR)";
    }

    @PreAuthorize("hasRole('USER')")
    @GetMapping("/user/profile")
    public String viewProfile() {
        return "사용자 프로필 페이지 (USER)";
    }

}
