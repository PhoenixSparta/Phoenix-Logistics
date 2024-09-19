package com.phoenix.logistics.core.delivery.domain;

public record Delivery(Order order, Hub hub, DeliveryStaff deliveryStaff, String fullAddress, Recipient recipient) {
}
