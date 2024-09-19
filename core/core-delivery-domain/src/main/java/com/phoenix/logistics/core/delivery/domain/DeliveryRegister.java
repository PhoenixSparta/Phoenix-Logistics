package com.phoenix.logistics.core.delivery.domain;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class DeliveryRegister {

    private final DeliveryRepository deliveryRepository;

    private final DeliveryLogAppender deliveryLogAppender;

    public DeliveryRegister(DeliveryRepository deliveryRepository, DeliveryLogAppender deliveryLogAppender) {
        this.deliveryRepository = deliveryRepository;
        this.deliveryLogAppender = deliveryLogAppender;
    }

    @Transactional
    public DeliveryWithUuid register(Delivery delivery) {
        DeliveryWithUuid deliveryWithUuid = deliveryRepository.add(delivery);
        DeliveryLog deliveryLog = new DeliveryLog(1, deliveryWithUuid, new Link(null, 0),
                new DeliveryStatus(CurrentStatus.PENDING));
        deliveryLogAppender.append(deliveryLog);
        return deliveryWithUuid;
    }

}
