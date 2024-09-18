package com.phoenix.logistics.core.hub.api.controller.v1.response;

import com.phoenix.logistics.core.hub.domain.HubResult;
import java.time.LocalDateTime;
import java.util.UUID;

public record HubResponse(UUID hubUuid, int sequence, String name, String city, String fullAddress, double latitude,
        double longitude, LocalDateTime createdAt, LocalDateTime updatedAt) {

    public static HubResponse of(HubResult hubResult) {
        return new HubResponse(hubResult.hubUuid(), hubResult.sequence(), hubResult.name(), hubResult.city(),
                hubResult.fullAddress(), hubResult.latitude(), hubResult.longitude(), hubResult.timestamp().createdAt(),
                hubResult.timestamp().updatedAt());
    }
}
