package com.phoenix.logistics.core.delivery.api.controller.v1.request;

import com.phoenix.logistics.core.delivery.domain.CurrentStatus;
import com.phoenix.logistics.core.delivery.domain.DeliveryStatus;

public record DeliveryStatusRequest(CurrentStatus currentStatus) {

    public DeliveryStatus toDeliveryStatus() {
        return new DeliveryStatus(currentStatus);
    }
}
