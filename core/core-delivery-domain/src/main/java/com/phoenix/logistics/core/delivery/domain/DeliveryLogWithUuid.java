package com.phoenix.logistics.core.delivery.domain;

import java.util.UUID;

public record DeliveryLogWithUuid(UUID deliveryLogUuid, DeliveryLog deliveryLog) {
}
