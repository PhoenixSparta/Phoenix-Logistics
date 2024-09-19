package com.phoenix.logistics.core.delivery.api.controller.v1.request;

import com.phoenix.logistics.core.delivery.domain.CurrentStatus;
import com.phoenix.logistics.core.delivery.domain.DeliveryRecord;
import com.phoenix.logistics.core.delivery.domain.DeliveryStatus;
import java.time.Duration;

public record DeliveryLogUpdateRequest(Duration actualDuration, double actualDistance, CurrentStatus currentStatus) {

    public DeliveryRecord toDeliveryRecord() {
        return new DeliveryRecord(actualDuration, actualDistance, new DeliveryStatus(currentStatus));
    }
}
