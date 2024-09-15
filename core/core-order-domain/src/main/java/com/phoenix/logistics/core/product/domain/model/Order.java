package com.phoenix.logistics.core.product.domain.model;

import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Builder
public class Order {

    private UUID uuid;

    private UUID manufacturerUuid;

    private UUID vendorUuid;

    private UUID deliveryUuid;

    private Long totalPrice;

}
