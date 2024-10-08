package com.phoenix.logistics.storage.db.core.delivery;

import com.phoenix.logistics.core.delivery.domain.DeliveryLog;
import com.phoenix.logistics.core.delivery.domain.DeliveryLogRepository;
import com.phoenix.logistics.core.delivery.domain.DeliveryLogResult;
import com.phoenix.logistics.core.delivery.domain.DeliveryLogWithUuid;
import com.phoenix.logistics.core.delivery.domain.DeliveryRecord;
import com.phoenix.logistics.core.delivery.domain.DeliveryStatus;
import java.util.UUID;
import org.springframework.stereotype.Repository;

@Repository
public class DeliveryLogCoreRepository implements DeliveryLogRepository {

    private final DeliveryLogJpaRepository deliveryLogJpaRepository;

    private final DeliveryJpaRepository deliveryJpaRepository;

    public DeliveryLogCoreRepository(DeliveryLogJpaRepository deliveryLogJpaRepository,
            DeliveryJpaRepository deliveryJpaRepository) {
        this.deliveryLogJpaRepository = deliveryLogJpaRepository;
        this.deliveryJpaRepository = deliveryJpaRepository;
    }

    @Override
    public DeliveryLogWithUuid add(DeliveryLog deliveryLog) {
        DeliveryEntity deliveryEntity = deliveryJpaRepository.findByUuid(deliveryLog.deliveryWithUuid().deliveryUuid())
            .orElseThrow();
        return deliveryLogJpaRepository.save(DeliveryLogEntity.of(deliveryLog, deliveryEntity)).toDeliveryLogWithUuid();
    }

    @Override
    public DeliveryLogResult read(UUID deliveryLogUuid) {
        return deliveryLogJpaRepository.findByUuid(deliveryLogUuid).orElseThrow().toDeliveryLogResult();
    }

    @Override
    public DeliveryLogResult updateDeliveryStatus(UUID deliveryLogUuid, DeliveryStatus deliveryStatus) {
        DeliveryLogEntity deliveryLogEntity = deliveryLogJpaRepository.findByUuid(deliveryLogUuid).orElseThrow();
        return deliveryLogEntity.updateCurrentStatus(deliveryStatus).toDeliveryLogResult();
    }

    @Override
    public DeliveryLogResult update(UUID deliveryLogUuid, DeliveryRecord deliveryRecord) {
        DeliveryLogEntity deliveryLogEntity = deliveryLogJpaRepository.findByUuid(deliveryLogUuid).orElseThrow();
        return deliveryLogEntity.update(deliveryRecord).toDeliveryLogResult();
    }

}
