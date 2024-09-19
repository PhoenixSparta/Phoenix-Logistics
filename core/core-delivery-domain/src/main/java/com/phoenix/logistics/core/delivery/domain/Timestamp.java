package com.phoenix.logistics.core.delivery.domain;

import java.time.LocalDateTime;

public record Timestamp(LocalDateTime createdAt, LocalDateTime updatedAt) {
}
