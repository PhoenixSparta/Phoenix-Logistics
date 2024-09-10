package com.phoenix.logistics.core.product;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.phoenix.logistics.core.product.application.service.ProductService;
import com.phoenix.logistics.core.product.domain.model.Product;
import com.phoenix.logistics.core.product.domain.repository.ProductRepository;

public class ProductServiceTest {

    @InjectMocks
    private ProductService productService;

    @Mock
    private ProductRepository productRepository;

    private UUID manufacturerUuid;

    private UUID hubUuid;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        manufacturerUuid = UUID.randomUUID();
        hubUuid = UUID.randomUUID();
    }

    @Test
    void createProduct_Success() {
        // Given
        String name = "Test Product";
        Integer price = 100;
        Integer stock = 50;

        Product product = Product.builder()
            .manufacturerUuid(manufacturerUuid)
            .hubUuid(hubUuid)
            .name(name)
            .price(price)
            .stock(stock)
            .build();

        when(productRepository.save(any(Product.class))).thenReturn(product);

        // When
        String createdProductName = productService.createProduct(manufacturerUuid, hubUuid, name, price, stock);

        // Then
        assertEquals(name, createdProductName);
    }

}
