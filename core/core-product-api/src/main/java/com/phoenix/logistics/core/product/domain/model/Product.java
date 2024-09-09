package com.phoenix.logistics.core.product.domain.model;

import java.util.UUID;

import lombok.Getter;

@Getter
public class Product {

    private UUID uuid;

    private UUID manufacturerUuid;

    private UUID hubUuid;

    private String name;

    private Integer stock;

    private Integer price;

}
