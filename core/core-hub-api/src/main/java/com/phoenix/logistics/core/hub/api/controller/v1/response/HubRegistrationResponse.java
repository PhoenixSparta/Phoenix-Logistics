package com.phoenix.logistics.core.hub.api.controller.v1.response;

import com.phoenix.logistics.core.hub.domain.HubWithUuid;
import java.util.UUID;

public record HubRegistrationResponse(UUID hubUuid) {

    public static HubRegistrationResponse of(HubWithUuid hubWithUuid) {
        return new HubRegistrationResponse(hubWithUuid.hubUuid());
    }
}
