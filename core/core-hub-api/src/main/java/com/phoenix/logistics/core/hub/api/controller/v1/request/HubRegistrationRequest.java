package com.phoenix.logistics.core.hub.api.controller.v1.request;

import com.phoenix.logistics.core.hub.domain.Hub;

public record HubRegistrationRequest(int sequence, String name, String city, String fullAddress, double latitude,
        double longitude) {

    public Hub toHub() {
        return new Hub(sequence, name, city, fullAddress, latitude, longitude);
    }
}
