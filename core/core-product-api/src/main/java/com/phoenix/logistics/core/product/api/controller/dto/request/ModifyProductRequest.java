package com.phoenix.logistics.core.product.api.controller.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record ModifyProductRequest(

        @NotBlank(message = "name is required") String name,

        @NotBlank(message = "description is required") String description,

        @NotNull(message = "price is required") Integer price,

        @NotNull(message = "stock is required") Integer stock

) {
}
