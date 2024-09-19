package com.phoenix.logistics.storage.db.core.order.entity;

import java.util.List;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

import com.phoenix.logistics.core.product.domain.model.Order;
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
@Table(name = "p_orders")
public class OrderEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "order_uuid")
    private UUID uuid;

    @Column(name = "manufacturer_uuid", nullable = false)
    private UUID manufacturerUuid;

    @Column(name = "vendor_uuid", nullable = false)
    private UUID vendorUuid;

    @Column(name = "delivery_uuid")
    private UUID deliveryUuid;

    @Column(name = "total_price", nullable = false)
    private Long totalPrice;

    @Column(nullable = false)
    private Boolean isDelete;

    public Order toDomain() {
        return Order.builder()
            .uuid(uuid)
            .manufacturerUuid(manufacturerUuid)
            .vendorUuid(vendorUuid)
            .deliveryUuid(deliveryUuid)
            .totalPrice(totalPrice)
            .build();
    }

}
