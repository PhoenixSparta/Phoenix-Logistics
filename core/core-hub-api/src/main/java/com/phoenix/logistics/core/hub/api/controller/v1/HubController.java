package com.phoenix.logistics.core.hub.api.controller.v1;

import com.phoenix.logistics.core.hub.api.controller.v1.request.HubRegistrationRequest;
import com.phoenix.logistics.core.hub.api.controller.v1.response.HubRegistrationResponse;
import com.phoenix.logistics.core.hub.api.controller.v1.response.HubResponse;
import com.phoenix.logistics.core.hub.api.support.response.ApiResponse;
import com.phoenix.logistics.core.hub.domain.HubResult;
import com.phoenix.logistics.core.hub.domain.HubService;
import com.phoenix.logistics.core.hub.domain.HubWithUuid;
import java.util.UUID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HubController {

    private final Logger log = LoggerFactory.getLogger(getClass());

    private final HubService hubService;

    public HubController(HubService hubService) {
        this.hubService = hubService;
    }

    @PostMapping("/api/v1/hubs")
    public ApiResponse<HubRegistrationResponse> registerHub(@RequestBody HubRegistrationRequest request) {
        HubWithUuid result = hubService.register(request.toHub());
        log.info("허브 등록 : {}", result);
        return ApiResponse.success(HubRegistrationResponse.of(result));
    }

    @GetMapping("/api/v1/hubs/{hubUuid}")
    public ApiResponse<HubResponse> readHub(@PathVariable String hubUuid) {
        HubResult result = hubService.read(UUID.fromString(hubUuid));
        log.info("허브 조회 : {}", result);
        return ApiResponse.success(HubResponse.of(result));
    }

}
