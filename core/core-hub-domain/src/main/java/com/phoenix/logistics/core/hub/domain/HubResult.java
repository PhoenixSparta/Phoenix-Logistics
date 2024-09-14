package com.phoenix.logistics.core.hub.domain;

import java.util.UUID;

public record HubResult(UUID hubUuid, int sequence, String name, String city, String fullAddress, double latitude,
        double longitude, Timestamp timestamp) {

}
