package com.phoenix.logistics.core.product.domain.client;

import java.util.UUID;

public interface DeliveryClient {

    UUID createDelivery(UUID manufacturerUuid, UUID vendorUuid, UUID orderUuid, UUID sourceHubUuid,
            UUID destinationHubUuid);

}
