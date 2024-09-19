package com.phoenix.logistics.core.delivery.domain;

import java.util.UUID;
import org.springframework.stereotype.Service;

@Service
public class DeliveryLogService {

    private final DeliveryLogReader deliveryLogReader;

    private final DeliveryLogModifier deliveryLogModifier;

    public DeliveryLogService(DeliveryLogReader deliveryLogReader, DeliveryLogModifier deliveryLogModifier) {
        this.deliveryLogReader = deliveryLogReader;
        this.deliveryLogModifier = deliveryLogModifier;
    }

    public DeliveryLogResult read(UUID deliveryLogUuid) {
        return deliveryLogReader.read(deliveryLogUuid);
    }

    public DeliveryLogResult updateDeliveryStatus(UUID deliveryLogUuid, DeliveryStatus deliveryStatus) {
        return deliveryLogModifier.updateDeliveryStatus(deliveryLogUuid, deliveryStatus);
    }

    public DeliveryLogResult update(UUID deliveryLogUuid, DeliveryRecord deliveryRecord) {
        return deliveryLogModifier.update(deliveryLogUuid, deliveryRecord);
    }

}
