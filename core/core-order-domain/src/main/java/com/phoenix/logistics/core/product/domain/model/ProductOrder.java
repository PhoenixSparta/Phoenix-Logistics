package com.phoenix.logistics.core.product.domain.model;

import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Builder
public class ProductOrder {

    private UUID productUuid;

    private UUID orderUuid;

    private Integer amount;

    private Long price;

}
