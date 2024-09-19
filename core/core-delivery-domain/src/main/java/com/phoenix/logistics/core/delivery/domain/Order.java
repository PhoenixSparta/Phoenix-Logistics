package com.phoenix.logistics.core.delivery.domain;

import java.util.UUID;

public record Order(UUID orderUuid, UUID manufacturerUuid, UUID vendorUuid) {
}
