package com.phoenix.logistics.core.delivery.domain;

import java.util.UUID;
import org.springframework.stereotype.Repository;

@Repository
public interface DeliveryRepository {

    DeliveryWithUuid add(Delivery delivery);

    DeliveryResult read(UUID deliveryUuid);

}
