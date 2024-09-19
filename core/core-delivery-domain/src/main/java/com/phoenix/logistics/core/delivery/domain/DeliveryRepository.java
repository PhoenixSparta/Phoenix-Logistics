package com.phoenix.logistics.core.delivery.domain;

import org.springframework.stereotype.Repository;

@Repository
public interface DeliveryRepository {

    DeliveryWithUuid add(Delivery delivery);

}
