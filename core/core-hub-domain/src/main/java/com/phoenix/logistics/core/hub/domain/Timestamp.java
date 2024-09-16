package com.phoenix.logistics.core.hub.domain;

import java.time.LocalDateTime;

public record Timestamp(LocalDateTime createdAt, LocalDateTime updatedAt) {
}
