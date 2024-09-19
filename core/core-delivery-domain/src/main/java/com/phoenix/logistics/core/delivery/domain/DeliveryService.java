package com.phoenix.logistics.core.delivery.domain;

import org.springframework.stereotype.Service;

@Service
public class DeliveryService {

    private final DeliveryRegister deliveryRegister;

    public DeliveryService(DeliveryRegister deliveryRegister) {
        this.deliveryRegister = deliveryRegister;
    }

    public DeliveryWithUuid register(Delivery delivery) {
        return deliveryRegister.register(delivery);
    }

}
