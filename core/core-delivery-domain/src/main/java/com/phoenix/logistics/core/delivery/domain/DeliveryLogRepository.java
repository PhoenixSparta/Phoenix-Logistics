package com.phoenix.logistics.core.delivery.domain;

import java.util.UUID;
import org.springframework.stereotype.Repository;

@Repository
public interface DeliveryLogRepository {

    DeliveryLogWithUuid add(DeliveryLog deliveryLog);

    DeliveryLogResult read(UUID deliveryLogUuid);

    DeliveryLogResult updateDeliveryStatus(UUID deliveryLogUuid, DeliveryStatus deliveryStatus);

    DeliveryLogResult update(UUID deliveryLogUuid, DeliveryRecord deliveryRecord);

}
