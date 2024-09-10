package com.phoenix.logistics.core.product.application.service;

import java.util.UUID;

import org.springframework.stereotype.Service;

import com.phoenix.logistics.core.product.domain.model.Product;
import com.phoenix.logistics.core.product.domain.repository.ProductRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;

    public String createProduct(UUID manufacturerUuid, UUID hubUuid, String name, Integer price, Integer stock) {
        Product product = Product.builder()
            .manufacturerUuid(manufacturerUuid)
            .hubUuid(hubUuid)
            .name(name)
            .price(price)
            .stock(stock)
            .build();
        return productRepository.save(product).getName();
    }

}
