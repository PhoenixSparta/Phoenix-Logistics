package com.phoenix.logistics.core.delivery.domain;

import java.util.UUID;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class DeliveryReader {

    private final DeliveryRepository deliveryRepository;

    public DeliveryReader(DeliveryRepository deliveryRepository) {
        this.deliveryRepository = deliveryRepository;
    }

    @Transactional(readOnly = true)
    public DeliveryResult read(UUID deliveryUuid) {
        return deliveryRepository.read(deliveryUuid);
    }

}
