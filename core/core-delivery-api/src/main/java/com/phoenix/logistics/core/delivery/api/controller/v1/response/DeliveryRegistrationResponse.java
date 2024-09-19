package com.phoenix.logistics.core.delivery.api.controller.v1.response;

import com.phoenix.logistics.core.delivery.domain.DeliveryWithUuid;
import java.util.UUID;

public record DeliveryRegistrationResponse(UUID deliveryUuid) {

    public static DeliveryRegistrationResponse of(DeliveryWithUuid deliveryWithUuid) {
        return new DeliveryRegistrationResponse(deliveryWithUuid.deliveryUuid());
    }
}
