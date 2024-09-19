package com.phoenix.logistics.core.delivery.api.controller.v1.response;

import com.phoenix.logistics.core.delivery.domain.DeliveryLogWithUuid;
import java.util.UUID;

public record DeliveryRegistrationResponse(UUID deliveryUuid, UUID deliveryLogUuid) {

    public static DeliveryRegistrationResponse of(DeliveryLogWithUuid deliveryLogWithUuid) {
        return new DeliveryRegistrationResponse(deliveryLogWithUuid.deliveryLog().deliveryWithUuid().deliveryUuid(),
                deliveryLogWithUuid.deliveryLogUuid());
    }
}
