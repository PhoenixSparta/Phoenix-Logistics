package com.phoenix.logistics.core.product.domain.service;

import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.phoenix.logistics.core.product.domain.model.Product;
import com.phoenix.logistics.core.product.domain.repository.ProductRepository;
import com.phoenix.logistics.support.model.DomainPage;

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

    public DomainPage<Product> searchProducts(String searchQuery, String sortBy, String direction, Integer page,
            Integer size) {
        return productRepository.searchProducts(searchQuery, sortBy, direction, page, size);
    }

    @Transactional
    public Product modifyProduct(UUID productUuid, String name, String description, Integer stock, Integer price) {
        Product targetProduct = productRepository.findById(productUuid);
        targetProduct.modify(name, description, stock, price);
        return productRepository.updateProduct(targetProduct);
    }

    public Product getProduct(UUID productUuid) {
        return productRepository.findById(productUuid);
    }

}
