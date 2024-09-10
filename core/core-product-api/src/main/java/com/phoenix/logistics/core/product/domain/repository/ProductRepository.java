package com.phoenix.logistics.core.product.domain.repository;

import org.springframework.data.repository.NoRepositoryBean;

import com.phoenix.logistics.core.product.domain.model.Product;

@NoRepositoryBean
public interface ProductRepository {

    Product save(Product product);

}
