package com.phoenix.logistics.core.delivery.api.controller.v1.response;

import com.phoenix.logistics.core.delivery.domain.CurrentStatus;
import com.phoenix.logistics.core.delivery.domain.DeliveryLogResult;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.UUID;

public record DeliveryLogResponse(UUID deliveryLogUuid, int sequence, UUID deliveryUuid, UUID orderUuid,
        UUID sourceHubUuid, UUID destinationHubUuid, Duration actualDuration, double actualDistance,
        CurrentStatus currentStatus, LocalDateTime createdAt, LocalDateTime updatedAt) {

    public static DeliveryLogResponse of(DeliveryLogResult deliveryLogResult) {
        return new DeliveryLogResponse(deliveryLogResult.deliveryLogWithUuid().deliveryLogUuid(),
                deliveryLogResult.deliveryLogWithUuid().deliveryLog().sequence(),
                deliveryLogResult.deliveryLogWithUuid().deliveryLog().deliveryWithUuid().deliveryUuid(),
                deliveryLogResult.deliveryLogWithUuid().deliveryLog().deliveryWithUuid().delivery().order().orderUuid(),
                deliveryLogResult.deliveryLogWithUuid()
                    .deliveryLog()
                    .deliveryWithUuid()
                    .delivery()
                    .hub()
                    .sourceHubUuid(),
                deliveryLogResult.deliveryLogWithUuid()
                    .deliveryLog()
                    .deliveryWithUuid()
                    .delivery()
                    .hub()
                    .destinationHubUuid(),
                deliveryLogResult.deliveryLogWithUuid().deliveryLog().link().actualDuration(),
                deliveryLogResult.deliveryLogWithUuid().deliveryLog().link().actualDistance(),
                deliveryLogResult.deliveryLogWithUuid().deliveryLog().deliveryStatus().currentStatus(),
                deliveryLogResult.timestamp().createdAt(), deliveryLogResult.timestamp().updatedAt());
    }
}
