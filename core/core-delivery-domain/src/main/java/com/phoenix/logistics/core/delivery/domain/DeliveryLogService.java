package com.phoenix.logistics.core.delivery.domain;

import java.util.UUID;
import org.springframework.stereotype.Service;

@Service
public class DeliveryLogService {

    private final DeliveryLogReader deliveryLogReader;

    public DeliveryLogService(DeliveryLogReader deliveryLogReader) {
        this.deliveryLogReader = deliveryLogReader;
    }

    public DeliveryLogResult read(UUID deliveryLogUuid) {
        return deliveryLogReader.read(deliveryLogUuid);
    }

}
