package com.phoenix.logistics.core.product;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.phoenix.logistics.core.product.application.service.ProductService;
import com.phoenix.logistics.core.product.domain.model.Product;
import com.phoenix.logistics.core.product.domain.repository.ProductRepository;
import com.phoenix.logistics.support.model.DomainPage;
import com.phoenix.logistics.support.response.ApiResponse;

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

    @Test
    void searchProducts_Success() {
        // Given
        String searchQuery = "test";
        String sortBy = "name";
        String direction = "asc";
        int page = 0;
        int size = 10;

        // Product 생성
        Product product1 = Product.builder()
            .uuid(UUID.randomUUID())
            .manufacturerUuid(UUID.randomUUID())
            .hubUuid(UUID.randomUUID())
            .name("Product 1")
            .price(100)
            .stock(10)
            .build();

        Product product2 = Product.builder()
            .uuid(UUID.randomUUID())
            .manufacturerUuid(UUID.randomUUID())
            .hubUuid(UUID.randomUUID())
            .name("Product 2")
            .price(150)
            .stock(20)
            .build();

        List<Product> products = Arrays.asList(product1, product2);

        // DomainPage 생성
        DomainPage<Product> productDomainPage = DomainPage.of(products, 2L, 1, page, size, false);

        // ProductRepository의 searchProducts 메서드 모킹
        when(productRepository.searchProducts(anyString(), anyString(), anyString(), anyInt(), anyInt()))
            .thenReturn(productDomainPage);

        // When
        ApiResponse<DomainPage<Product>> response = productService.searchProducts(searchQuery, sortBy, direction, page,
                size);

        // Then
        assertEquals(2, response.getData().getData().size());
        assertEquals("Product 1", response.getData().getData().get(0).getName());
        assertEquals("Product 2", response.getData().getData().get(1).getName());
        assertEquals(2L, response.getData().getTotalItems());
        assertEquals(1, response.getData().getTotalPages());
        assertEquals(page, response.getData().getCurrentPage());
        assertEquals(size, response.getData().getPageSize());
        assertEquals(false, response.getData().getHasNext());
    }

}
