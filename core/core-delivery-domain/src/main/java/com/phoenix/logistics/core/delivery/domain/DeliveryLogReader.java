package com.phoenix.logistics.core.delivery.domain;

import java.util.UUID;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class DeliveryLogReader {

    private final DeliveryLogRepository deliveryLogRepository;

    public DeliveryLogReader(DeliveryLogRepository deliveryLogRepository) {
        this.deliveryLogRepository = deliveryLogRepository;
    }

    @Transactional(readOnly = true)
    public DeliveryLogResult read(UUID deliveryLogUuid) {
        return deliveryLogRepository.read(deliveryLogUuid);
    }

}
