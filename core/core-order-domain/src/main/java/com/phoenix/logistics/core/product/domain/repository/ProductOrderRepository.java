package com.phoenix.logistics.core.product.domain.repository;

import java.util.List;
import java.util.UUID;

import com.phoenix.logistics.core.product.domain.model.ProductOrder;

public interface ProductOrderRepository {

    ProductOrder save(ProductOrder productOrder);

    List<ProductOrder> saveAll(List<ProductOrder> productOrders, UUID orderUuid);

    List<ProductOrder> findByOrderUuid(UUID orderUuid);

}
