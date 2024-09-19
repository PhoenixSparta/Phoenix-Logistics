package com.phoenix.logistics.storage.db.core.order.implement;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.phoenix.logistics.core.product.domain.model.ProductOrder;
import com.phoenix.logistics.storage.db.core.order.entity.ProductOrderEntity;
import com.phoenix.logistics.storage.db.core.order.entity.ProductOrderPk;

public interface JpaProductOrderRepository extends JpaRepository<ProductOrderEntity, ProductOrderPk> {

    List<ProductOrderEntity> findByOrderUuid(UUID orderUuid);

}
