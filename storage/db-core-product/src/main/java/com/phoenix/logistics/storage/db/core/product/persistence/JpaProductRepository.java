package com.phoenix.logistics.storage.db.core.product.persistence;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.phoenix.logistics.storage.db.core.product.entity.ProductEntity;

public interface JpaProductRepository extends JpaRepository<ProductEntity, UUID> {

}
