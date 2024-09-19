package com.phoenix.logistics.storage.db.core.order.implement;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Repository;

import com.phoenix.logistics.core.product.domain.model.ProductOrder;
import com.phoenix.logistics.core.product.domain.repository.ProductOrderRepository;
import com.phoenix.logistics.storage.db.core.order.entity.ProductOrderEntity;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class ProductOrderRepositoryAdapter implements ProductOrderRepository {

    private final JpaProductOrderRepository jpaProductOrderRepository;

    @Override
    public ProductOrder save(ProductOrder productOrder) {
        ProductOrderEntity productOrderEntity = ProductOrderEntity.builder()
            .productUuid(productOrder.getProductUuid())
            .orderUuid(productOrder.getOrderUuid())
            .price(productOrder.getPrice())
            .amount(productOrder.getAmount())
            .isDelete(false)
            .build();

        return jpaProductOrderRepository.save(productOrderEntity).toDomain();
    }

    @Override
    public List<ProductOrder> saveAll(List<ProductOrder> productOrders, UUID orderUuid) {
        List<ProductOrderEntity> productOrderEntityList = productOrders.stream()
            .map(productOrder -> ProductOrderEntity.builder()
                .productUuid(productOrder.getProductUuid())
                .orderUuid(orderUuid)
                .price(productOrder.getPrice())
                .amount(productOrder.getAmount())
                .isDelete(false)
                .build())
            .toList();

        return jpaProductOrderRepository.saveAll(productOrderEntityList)
            .stream()
            .map(ProductOrderEntity::toDomain)
            .toList();
    }

    @Override
    public List<ProductOrder> findByOrderUuid(UUID orderUuid) {
        return jpaProductOrderRepository.findByOrderUuid(orderUuid).stream().map(ProductOrderEntity::toDomain).toList();
    }

}
