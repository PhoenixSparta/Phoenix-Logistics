package com.phoenix.logistics.storage.db.core.product.entity;

import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import com.phoenix.logistics.core.product.domain.model.Product;
import com.phoenix.logistics.support.entity.BaseEntity;

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
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "product_uuid")
    private UUID uuid;

    @Column(name = "manufacturer_uuid", nullable = false)
    private UUID manufacturerUuid;

    @Column(name = "hub_uuid", nullable = false)
    private UUID hubUuuid;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String description;

    @Column(nullable = false)
    private Integer stock;

    @Column(nullable = false)
    private Integer price;

    @Column(nullable = false)
    private Boolean isDelete;

    @Builder
    public ProductEntity(UUID manufacturerUuid, UUID hubUuid, String name, String description, Integer stock,
            Integer price, Boolean isDelete) {
        this.manufacturerUuid = manufacturerUuid;
        this.hubUuuid = hubUuid;
        this.name = name;
        this.description = description;
        this.stock = stock;
        this.price = price;
        this.isDelete = isDelete;
    }

    public Product toDomain() {
        return Product.builder()
            .uuid(uuid)
            .manufacturerUuid(manufacturerUuid)
            .hubUuid(hubUuuid)
            .name(name)
            .description(description)
            .stock(stock)
            .price(price)
            .build();
    }

    public void update(String name, String description, Integer stock, Integer price) {
        this.name = name;
        this.description = description;
        this.stock = stock;
        this.price = price;
    }

}
