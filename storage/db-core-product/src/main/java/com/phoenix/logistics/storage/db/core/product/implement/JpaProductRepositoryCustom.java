package com.phoenix.logistics.storage.db.core.product.implement;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.phoenix.logistics.storage.db.core.product.entity.ProductEntity;

public interface JpaProductRepositoryCustom {

    Page<ProductEntity> searchProducts(String searchQuery, Pageable pageable);

}
