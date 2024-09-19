package com.phoenix.logistics.core.delivery.domain;

import java.util.UUID;

public record Hub(UUID sourceHubUuid, UUID destinationHubUuid) {
}
