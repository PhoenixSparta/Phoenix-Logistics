package com.phoenix.logistics.core.delivery.domain;

import java.util.UUID;
import org.springframework.stereotype.Service;

@Service
public class DeliveryService {

    private final DeliveryRegister deliveryRegister;

    private final DeliveryReader deliveryReader;

    public DeliveryService(DeliveryRegister deliveryRegister, DeliveryReader deliveryReader) {
        this.deliveryRegister = deliveryRegister;
        this.deliveryReader = deliveryReader;
    }

    public DeliveryLogWithUuid register(Delivery delivery) {
        return deliveryRegister.register(delivery);
    }

    public DeliveryResult read(UUID deliveryUuid) {
        return deliveryReader.read(deliveryUuid);
    }

}
