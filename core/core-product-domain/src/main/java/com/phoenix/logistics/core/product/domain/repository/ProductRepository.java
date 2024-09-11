package com.phoenix.logistics.core.product.domain.repository;

import com.phoenix.logistics.core.product.domain.model.Product;

public interface ProductRepository {

    Product save(Product product);

}
