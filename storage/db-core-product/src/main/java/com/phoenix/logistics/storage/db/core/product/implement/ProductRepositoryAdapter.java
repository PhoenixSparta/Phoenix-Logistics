package com.phoenix.logistics.storage.db.core.product.implement;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;

import com.phoenix.logistics.core.product.domain.model.DomainPage;
import com.phoenix.logistics.core.product.domain.model.Product;
import com.phoenix.logistics.core.product.domain.repository.ProductRepository;
import com.phoenix.logistics.storage.db.core.product.entity.ProductEntity;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class ProductRepositoryAdapter implements ProductRepository {

    private final JpaProductRepository jpaProductRepository;

    @Override
    public Product save(Product product) {
        ProductEntity productEntity = ProductEntity.builder()
            .manufacturerUuid(product.getManufacturerUuid())
            .hubUuid(product.getHubUuid())
            .name(product.getName())
            .stock(product.getStock())
            .price(product.getPrice())
            .isDelete(false)
            .build();

        return jpaProductRepository.save(productEntity).toDomain();
    }

    @Override
    public DomainPage<Product> searchProducts(String searchQuery, String sortBy, String direction, int page, int size) {

        Sort sort = Sort.by(Sort.Direction.fromString(direction), sortBy);
        Pageable pageable = PageRequest.of(page, size, sort);

        Page<Product> productPage = jpaProductRepository.searchProducts(searchQuery, pageable)
            .map(ProductEntity::toDomain);

        // spring-data-page -> custom page 매핑
        return DomainPage.of(productPage.getContent(), productPage.getTotalElements(), productPage.getTotalPages(),
                productPage.getNumber(), productPage.getSize(), productPage.hasNext());
    }

}
