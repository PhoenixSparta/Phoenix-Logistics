package com.phoenix.logistics.core.product.domain.model;

import java.util.UUID;

public record DeliveryUuidWithLogUuid(UUID deliveryUuid, UUID deliveryLogUuid) {
}
