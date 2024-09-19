package com.phoenix.logistics.core.order.api.controller.dto;

import java.util.UUID;

import jakarta.validation.constraints.NotNull;

public record ProductOrderDto(

        @NotNull UUID productUuid,

        @NotNull Integer amount,

        @NotNull Long price

) {
}
