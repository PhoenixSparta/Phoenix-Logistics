package com.phoenix.logistics.core.product.presentation.controller.dto.request;

import java.util.UUID;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CreateProductRequest(@NotNull(message = "manufacturerUuid is required") UUID manufacturerUuid,

        @NotNull(message = "hubUuid is required") UUID hubUuid,

        @NotBlank(message = "name is required") String name,

        @NotNull(message = "price is required") Integer price,

        @NotNull(message = "stock is required") Integer stock) {
}
