package com.phoenix.logistics.core.product.domain.repository;

import com.phoenix.logistics.core.product.domain.model.Product;
import com.phoenix.logistics.support.model.DomainPage;

public interface ProductRepository {

    Product save(Product product);

    DomainPage<Product> searchProducts(String searchQuery, String sortBy, String direction, int pageNo, int pageSize);

}
