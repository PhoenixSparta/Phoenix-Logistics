package com.phoenix.logistics.storage.db.core.order.entity;

import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.Table;

import com.phoenix.logistics.support.entity.BaseEntity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "p_products_orders")
@IdClass(ProductOrderPk.class)
public class ProductOrderEntity extends BaseEntity {

    @Id
    @Column(name = "product_id")
    private UUID productUuid;

    @Id
    @Column(name = "order_id")
    private UUID orderUuid;

    @Column(nullable = false)
    private Integer amount;

    @Column(nullable = false)
    private Integer price;

    @Column(nullable = false)
    private Boolean isDelete;

}
