package com.phoenix.logistics.core.delivery.domain;

import java.util.UUID;

public record DeliveryWithUuid(UUID deliveryUuid, Delivery delivery) {
}
