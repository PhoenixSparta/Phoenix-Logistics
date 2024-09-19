package com.phoenix.logistics.core.delivery.domain;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class DeliveryLogAppender {

    private final DeliveryLogRepository deliveryLogRepository;

    public DeliveryLogAppender(DeliveryLogRepository deliveryLogRepository) {
        this.deliveryLogRepository = deliveryLogRepository;
    }

    @Transactional
    public DeliveryLogWithUuid append(DeliveryLog deliveryLog) {
        return deliveryLogRepository.add(deliveryLog);
    }

}
