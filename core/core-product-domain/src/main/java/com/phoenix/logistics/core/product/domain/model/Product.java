package com.phoenix.logistics.core.product.domain.model;

import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Builder
public class Product {

    private UUID uuid;

    private UUID manufacturerUuid;

    private UUID hubUuid;

    private String name;

    private String description;

    private Integer stock;

    private Integer price;

    public void modify(String name, String description, Integer stock, Integer price) {
        this.name = name;
        this.description = description;
        this.stock = stock;
        this.price = price;
    }

}
