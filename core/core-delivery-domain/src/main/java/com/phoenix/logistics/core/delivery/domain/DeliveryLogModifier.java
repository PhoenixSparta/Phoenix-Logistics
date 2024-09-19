package com.phoenix.logistics.core.delivery.domain;

import java.util.UUID;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class DeliveryLogModifier {

    private final DeliveryLogRepository deliveryLogRepository;

    public DeliveryLogModifier(DeliveryLogRepository deliveryLogRepository) {
        this.deliveryLogRepository = deliveryLogRepository;
    }

    @Transactional
    public DeliveryLogResult updateDeliveryStatus(UUID deliveryLogUuid, DeliveryStatus deliveryStatus) {
        return deliveryLogRepository.updateDeliveryStatus(deliveryLogUuid, deliveryStatus);
    }

    @Transactional
    public DeliveryLogResult update(UUID deliveryLogUuid, DeliveryRecord deliveryRecord) {
        return deliveryLogRepository.update(deliveryLogUuid, deliveryRecord);
    }

}
