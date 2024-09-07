package com.phoenix.logistics.storage.db.core.product.entity;

import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "p_products")
public class ProductEntity extends BaseEntity {

    @Id
    @Column(name = "product_uuid")
    private UUID uuid;

    @Column(name = "manufacture_uuid")
    private UUID manufacturerUUID;

    @Column(name = "hub_uuid")
    private UUID hubUUID;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private Integer stock;

    @Column(nullable = false)
    private Integer price;

    @Column(nullable = false)
    private Boolean isDelete;

    @Builder
    public ProductEntity(UUID manufacturerUUID, UUID hubUUID, String name, Integer stock, Integer price,
            Boolean isDelete) {
        this.manufacturerUUID = manufacturerUUID;
        this.hubUUID = hubUUID;
        this.name = name;
        this.stock = stock;
        this.price = price;
        this.isDelete = isDelete;
    }

}
