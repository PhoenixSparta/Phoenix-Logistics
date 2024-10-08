package com.phoenix.logistics.storage.db.core.delivery;

import com.phoenix.logistics.core.delivery.domain.Delivery;
import com.phoenix.logistics.core.delivery.domain.DeliveryRepository;
import com.phoenix.logistics.core.delivery.domain.DeliveryResult;
import com.phoenix.logistics.core.delivery.domain.DeliveryWithUuid;
import java.util.UUID;
import org.springframework.stereotype.Repository;

@Repository
public class DeliveryCoreRepository implements DeliveryRepository {

    private final DeliveryJpaRepository deliveryJpaRepository;

    public DeliveryCoreRepository(DeliveryJpaRepository deliveryJpaRepository) {
        this.deliveryJpaRepository = deliveryJpaRepository;
    }

    @Override
    public DeliveryWithUuid add(Delivery delivery) {
        return deliveryJpaRepository.save(DeliveryEntity.of(delivery)).toDeliveryWithUuid();
    }

    @Override
    public DeliveryResult read(UUID deliveryUuid) {
        return deliveryJpaRepository.findByUuid(deliveryUuid).orElseThrow().toDeliveryResult();
    }

}
