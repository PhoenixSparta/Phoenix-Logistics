package com.phoenix.logistics.core.delivery.api.controller.v1.request;

import com.phoenix.logistics.core.delivery.domain.Delivery;
import com.phoenix.logistics.core.delivery.domain.DeliveryStaff;
import com.phoenix.logistics.core.delivery.domain.Hub;
import com.phoenix.logistics.core.delivery.domain.Order;
import com.phoenix.logistics.core.delivery.domain.Recipient;
import java.util.UUID;

public record DeliveryRegistrationRequest(UUID orderUuid, UUID manufacturerUuid, UUID vendorUuid, UUID sourceHubUuid,
        UUID destinationHubUuid, UUID hubDeliverStaffUuid, UUID companyDeliveryStaffUuid, String fullAddress,
        String recipientName, String recipientSlackId) {

    public Delivery toDelivery() {
        return new Delivery(new Order(orderUuid, manufacturerUuid, vendorUuid),
                new Hub(sourceHubUuid, destinationHubUuid),
                new DeliveryStaff(hubDeliverStaffUuid, companyDeliveryStaffUuid), fullAddress,
                new Recipient(recipientName, recipientSlackId));
    }
}
