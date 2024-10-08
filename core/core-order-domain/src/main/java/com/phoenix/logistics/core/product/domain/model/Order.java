package com.phoenix.logistics.core.product.domain.model;

import java.util.List;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@AllArgsConstructor
@Builder
public class Order {

    private UUID uuid;

    private UUID manufacturerUuid;

    private UUID vendorUuid;

    @Setter
    private UUID deliveryUuid;

    @Setter
    private UUID deliveryLogUuid;

    private Long totalPrice;

    @Setter
    private List<ProductOrder> productOrderList;

}
