package com.phoenix.logistics.core.delivery.domain;

import java.time.Duration;

public record DeliveryRecord(Duration actualDuration, double actualDistance, DeliveryStatus deliveryStatus) {
}
