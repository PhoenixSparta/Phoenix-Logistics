package com.phoenix.logistics.core.hub.domain;

import java.util.UUID;

public record HubWithUuid(UUID hubUuid, int sequence, String name, String city, String fullAddress, double latitude,
        double longitude) {

}
