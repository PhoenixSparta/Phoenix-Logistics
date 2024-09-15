package com.phoenix.logistics.storage.db.core.order.implement;

import org.springframework.stereotype.Repository;

import com.phoenix.logistics.core.product.domain.repository.OrderRepository;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class OrderRepositoryAdapter implements OrderRepository {

	private final JpaOrderRepository jpaOrderRepository;

}
