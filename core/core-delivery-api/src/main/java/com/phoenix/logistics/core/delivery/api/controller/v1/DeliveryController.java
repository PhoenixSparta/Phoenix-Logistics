package com.phoenix.logistics.core.delivery.api.controller.v1;

import com.phoenix.logistics.core.delivery.api.controller.v1.request.DeliveryRegistrationRequest;
import com.phoenix.logistics.core.delivery.api.controller.v1.response.DeliveryRegistrationResponse;
import com.phoenix.logistics.core.delivery.api.support.response.ApiResponse;
import com.phoenix.logistics.core.delivery.domain.DeliveryService;
import com.phoenix.logistics.core.delivery.domain.DeliveryWithUuid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DeliveryController {

    private final Logger log = LoggerFactory.getLogger(getClass());

    private final DeliveryService deliveryService;

    public DeliveryController(DeliveryService deliveryService) {
        this.deliveryService = deliveryService;
    }

    @PostMapping("/api/v1/delivery")
    public ApiResponse<DeliveryRegistrationResponse> registerDelivery(
            @RequestBody DeliveryRegistrationRequest request) {
        DeliveryWithUuid result = deliveryService.register(request.toDelivery());
        log.info("배송 등록 : {}", result);
        return ApiResponse.success(DeliveryRegistrationResponse.of(result));
    }

}
