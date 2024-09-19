package com.phoenix.logistics.core.product.domain.client;

import com.phoenix.logistics.core.product.domain.model.DeliveryUuidWithLogUuid;
import java.util.UUID;

public interface DeliveryClient {

    DeliveryUuidWithLogUuid createDelivery(UUID manufacturerUuid, UUID vendorUuid, UUID orderUuid, UUID sourceHubUuid,
            UUID destinationHubUuid, String fullAddress, String recipientName, String recipientSlackId);

}
