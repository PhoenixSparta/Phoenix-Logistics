package com.phoenix.logistics.core.product.domain.repository;

import com.phoenix.logistics.core.product.domain.model.DomainPage;
import com.phoenix.logistics.core.product.domain.model.Product;

public interface ProductRepository {

    Product save(Product product);

    DomainPage<Product> searchProducts(String searchQuery, String sortBy, String direction, int pageNo, int pageSize);

}
