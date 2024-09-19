package com.phoenix.logistics.core.delivery.domain;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class DeliveryRegister {

    private final DeliveryRepository deliveryRepository;

    public DeliveryRegister(DeliveryRepository deliveryRepository) {
        this.deliveryRepository = deliveryRepository;
    }

    @Transactional
    public DeliveryWithUuid register(Delivery delivery) {
        return deliveryRepository.add(delivery);
    }

}
