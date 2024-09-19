package com.phoenix.logistics.core.order.api.controller.dto;

import java.util.List;
import java.util.UUID;

import jakarta.validation.constraints.NotNull;

public record CreateOrderRequest(

        @NotNull UUID manufacturerUuid,

        @NotNull UUID vendorUuid,

        @NotNull List<ProductOrderDto> productOrderDtoList

) {
}
