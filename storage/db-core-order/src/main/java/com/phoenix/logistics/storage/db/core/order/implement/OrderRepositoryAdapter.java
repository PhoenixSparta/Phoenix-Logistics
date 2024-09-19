package com.phoenix.logistics.storage.db.core.order.implement;

import java.util.UUID;

import jakarta.persistence.EntityNotFoundException;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.phoenix.logistics.core.product.domain.model.Order;
import com.phoenix.logistics.core.product.domain.repository.OrderRepository;
import com.phoenix.logistics.storage.db.core.order.entity.OrderEntity;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class OrderRepositoryAdapter implements OrderRepository {

    private final JpaOrderRepository jpaOrderRepository;

    @Override
    public Order save(Order order) {
        OrderEntity orderEntity = OrderEntity.builder()
            .manufacturerUuid(order.getManufacturerUuid())
            .vendorUuid(order.getVendorUuid())
            .totalPrice(order.getTotalPrice())
            .isDelete(false)
            .build();

        return jpaOrderRepository.save(orderEntity).toDomain();
    }

    @Override
    public Order findByOrderUuid(UUID orderUuid) {
        return jpaOrderRepository.findById(orderUuid).orElseThrow(EntityNotFoundException::new).toDomain();
    }

    @Override
    @Transactional
    public void setDelivery(UUID orderUuid, UUID deliveryUuid) {
        OrderEntity targetOrderEntity = jpaOrderRepository.findById(orderUuid)
            .orElseThrow(EntityNotFoundException::new);
        targetOrderEntity.setDelivery(deliveryUuid);
    }

}
