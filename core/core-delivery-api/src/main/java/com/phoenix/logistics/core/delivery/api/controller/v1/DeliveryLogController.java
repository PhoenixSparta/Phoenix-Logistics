package com.phoenix.logistics.core.delivery.api.controller.v1;

import com.phoenix.logistics.core.delivery.api.controller.v1.response.DeliveryLogResponse;
import com.phoenix.logistics.core.delivery.api.support.response.ApiResponse;
import com.phoenix.logistics.core.delivery.domain.DeliveryLogResult;
import com.phoenix.logistics.core.delivery.domain.DeliveryLogService;
import java.util.UUID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DeliveryLogController {

    private final Logger log = LoggerFactory.getLogger(getClass());

    private final DeliveryLogService deliveryLogService;

    public DeliveryLogController(DeliveryLogService deliveryLogService) {
        this.deliveryLogService = deliveryLogService;
    }

    @GetMapping("/api/v1/delivery/logs/{deliveryLogUuid}")
    public ApiResponse<DeliveryLogResponse> readDeliveryLog(@PathVariable String deliveryLogUuid) {
        DeliveryLogResult result = deliveryLogService.read(UUID.fromString(deliveryLogUuid));
        log.info("배송 로그 조회 : {}", result);
        return ApiResponse.success(DeliveryLogResponse.of(result));
    }

}
