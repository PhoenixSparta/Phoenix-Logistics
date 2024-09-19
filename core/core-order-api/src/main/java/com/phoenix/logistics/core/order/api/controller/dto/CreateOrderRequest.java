package com.phoenix.logistics.core.order.api.controller.dto;

import jakarta.validation.constraints.NotNull;
import java.util.List;
import java.util.UUID;

public record CreateOrderRequest(

        @NotNull UUID manufacturerUuid,

        @NotNull UUID vendorUuid,

        @NotNull String fullAddress,

        @NotNull String recipientName,

        @NotNull String recipientSlackId,

        @NotNull List<ProductOrderDto> productOrderDtoList

) {
}
