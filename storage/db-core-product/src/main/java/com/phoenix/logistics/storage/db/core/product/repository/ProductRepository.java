package com.phoenix.logistics.storage.db.core.product.repository;

import java.util.UUID;

import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.Repository;

import com.phoenix.logistics.storage.db.core.product.entity.ProductEntity;

@NoRepositoryBean
public interface ProductRepository extends Repository<ProductEntity, UUID> {

}
