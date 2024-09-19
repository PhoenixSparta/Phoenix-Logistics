package com.phoenix.logistics.core.delivery.domain;

public record DeliveryLog(int sequence, DeliveryWithUuid deliveryWithUuid, Link link, DeliveryStatus deliveryStatus) {
}
