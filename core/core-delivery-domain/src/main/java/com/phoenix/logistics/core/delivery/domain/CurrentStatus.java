package com.phoenix.logistics.core.delivery.domain;

public enum CurrentStatus {

    PENDING("배송 대기중"), DELIVERING_TO_HUB("허브 배송중"), ARRIVED_AT_HUB("허브 도착"), DELIVERING_TO_COMPANY("업체 배송중"),
    COMPLETED("배송 완료");

    private final String description;

    CurrentStatus(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

}
