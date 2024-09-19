package com.phoenix.logistics.client.delivery.dto;

import java.util.UUID;

public record CreateDeliveryRequest(

        UUID manufacturerUuid,

        UUID vendorUuid,

        UUID orderUuid,

        UUID sourceHubUuid,

        UUID destinationHubUuid

) {
}
