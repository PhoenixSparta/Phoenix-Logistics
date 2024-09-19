package com.phoenix.logistics.client.delivery;

import java.util.UUID;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

import com.phoenix.logistics.client.delivery.dto.CreateDeliveryRequest;

@FeignClient(value = "delivery-api", url = "{delivery.api.url}")
public interface FeignDeliveryClient {

    @PostMapping("/api/v1/delivery")
    UUID createDelivery(CreateDeliveryRequest createDeliveryRequest);

}
