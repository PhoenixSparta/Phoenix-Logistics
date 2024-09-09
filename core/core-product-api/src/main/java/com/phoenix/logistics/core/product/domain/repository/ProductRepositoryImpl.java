package com.phoenix.logistics.core.product.domain.repository;

import com.phoenix.logistics.storage.db.core.product.persistence.JpaProductRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class ProductRepositoryImpl implements ProductRepository {

    private final JpaProductRepository jpaProductRepository;

}
