package com.phoenix.logistics.storage.db.core.order.implement;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.phoenix.logistics.storage.db.core.order.entity.OrderEntity;

public interface JpaOrderRepository extends JpaRepository<OrderEntity, UUID> {

}
