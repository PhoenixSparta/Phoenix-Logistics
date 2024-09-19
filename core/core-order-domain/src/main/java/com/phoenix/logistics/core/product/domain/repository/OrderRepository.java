package com.phoenix.logistics.core.product.domain.repository;

import java.util.UUID;

import com.phoenix.logistics.core.product.domain.model.Order;

public interface OrderRepository {

    Order save(Order order);

    Order findByOrderUuid(UUID orderUuid);

}
