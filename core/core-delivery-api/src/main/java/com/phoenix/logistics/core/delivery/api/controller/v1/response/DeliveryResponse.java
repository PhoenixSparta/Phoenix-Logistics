package com.phoenix.logistics.core.delivery.api.controller.v1.response;

import com.phoenix.logistics.core.delivery.domain.DeliveryResult;
import java.time.LocalDateTime;
import java.util.UUID;

public record DeliveryResponse(UUID deliveryUuid, UUID orderUuid, UUID manufacturerUuid, UUID vendorUuid,
        UUID sourceHubUuid, UUID destinationHubUuid, UUID hubDeliveryStaffUuid, UUID companyDeliveryStaffUuid,
        String fullAddress, String recipientName, String recipientSlackId, LocalDateTime createdAt,
        LocalDateTime updatedAt) {

    public static DeliveryResponse of(DeliveryResult deliveryResult) {
        return new DeliveryResponse(deliveryResult.deliveryWithUuid().deliveryUuid(),
                deliveryResult.deliveryWithUuid().delivery().order().orderUuid(),
                deliveryResult.deliveryWithUuid().delivery().order().manufacturerUuid(),
                deliveryResult.deliveryWithUuid().delivery().order().vendorUuid(),
                deliveryResult.deliveryWithUuid().delivery().hub().sourceHubUuid(),
                deliveryResult.deliveryWithUuid().delivery().hub().destinationHubUuid(),
                deliveryResult.deliveryWithUuid().delivery().deliveryStaff().hubDeliveryStaffUuid(),
                deliveryResult.deliveryWithUuid().delivery().deliveryStaff().companyDeliveryStaffUuid(),
                deliveryResult.deliveryWithUuid().delivery().fullAddress(),
                deliveryResult.deliveryWithUuid().delivery().recipient().recipientName(),
                deliveryResult.deliveryWithUuid().delivery().recipient().recipientSlackId(),
                deliveryResult.timestamp().createdAt(), deliveryResult.timestamp().updatedAt());
    }
}
