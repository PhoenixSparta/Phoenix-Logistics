package com.phoenix.logistics.core.product.domain.repository;

import org.springframework.stereotype.Repository;

import com.phoenix.logistics.core.product.domain.model.Product;
import com.phoenix.logistics.storage.db.core.product.entity.ProductEntity;
import com.phoenix.logistics.storage.db.core.product.persistence.JpaProductRepository;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class ProductRepositoryImpl implements ProductRepository {

    private final JpaProductRepository jpaProductRepository;

    @Override
    public Product save(Product product) {
        ProductEntity productEntity = ProductEntity.builder()
            .manufacturerUuid(product.getManufacturerUuid())
            .hubUuid(product.getHubUuid())
            .name(product.getName())
            .stock(product.getStock())
            .price(product.getPrice())
            .isDelete(true)
            .build();
        jpaProductRepository.save(productEntity);
        // todo 모듈 분리 후 Entity to Domain 로직 작성후 생성된 Entity 기반 객체로 변경
        return product;
    }

}
