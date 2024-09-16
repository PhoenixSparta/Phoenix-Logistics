package com.phoenix.logistics.core.product.domain;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.phoenix.logistics.core.product.domain.model.Product;
import com.phoenix.logistics.core.product.domain.repository.ProductRepository;
import com.phoenix.logistics.core.product.domain.service.ProductService;
import com.phoenix.logistics.support.model.DomainPage;

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
        String description = "Test Description";
        Integer price = 100;
        Integer stock = 50;

        Product product = Product.builder()
            .manufacturerUuid(manufacturerUuid)
            .hubUuid(hubUuid)
            .name(name)
            .description(description)
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
            .description("Product 1 description")
            .price(100)
            .stock(10)
            .build();

        Product product2 = Product.builder()
            .uuid(UUID.randomUUID())
            .manufacturerUuid(UUID.randomUUID())
            .hubUuid(UUID.randomUUID())
            .name("Product 2")
            .description("Product 2 description")
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
        DomainPage<Product> response = productService.searchProducts(searchQuery, sortBy, direction, page, size);

        // Then
        assertEquals(2, response.getData().size());
        assertEquals("Product 1", response.getData().get(0).getName());
        assertEquals("Product 2", response.getData().get(1).getName());
        assertEquals("Product 1 description", response.getData().get(0).getDescription());
        assertEquals("Product 2 description", response.getData().get(1).getDescription());
        assertEquals(2L, response.getTotalItems());
        assertEquals(1, response.getTotalPages());
        assertEquals(page, response.getCurrentPage());
        assertEquals(size, response.getPageSize());
        assertEquals(false, response.getHasNext());
    }

    @Test
    void modifyProduct_Success() {
        // Given
        UUID productUuid = UUID.randomUUID();
        String updatedName = "Updated Product";
        String updatedDescription = "Updated Description";
        Integer updatedStock = 100;
        Integer updatedPrice = 200;

        // 기존 Product 객체 생성
        Product existingProduct = Product.builder()
            .uuid(productUuid)
            .manufacturerUuid(UUID.randomUUID())
            .hubUuid(UUID.randomUUID())
            .name("Original Product")
            .description("Original Description")
            .stock(50)
            .price(100)
            .build();

        // 수정된 Product 객체 생성
        Product modifiedProduct = Product.builder()
            .uuid(productUuid)
            .manufacturerUuid(existingProduct.getManufacturerUuid())
            .hubUuid(existingProduct.getHubUuid())
            .name(updatedName)
            .description(updatedDescription)
            .stock(updatedStock)
            .price(updatedPrice)
            .build();

        // ProductRepository의 메서드 모킹
        when(productRepository.findById(productUuid)).thenReturn(existingProduct);
        when(productRepository.updateProduct(any(Product.class))).thenReturn(modifiedProduct);

        // When
        Product resultProduct = productService.modifyProduct(productUuid, updatedName, updatedDescription, updatedStock,
                updatedPrice);

        // Then
        assertEquals(updatedName, resultProduct.getName());
        assertEquals(updatedDescription, resultProduct.getDescription());
        assertEquals(updatedStock, resultProduct.getStock());
        assertEquals(updatedPrice, resultProduct.getPrice());
    }

    @Test
    void getProduct_Success() {
        // Given
        UUID productUuid = UUID.randomUUID();

        // 테스트용 Product 객체 생성
        Product product = Product.builder()
            .uuid(productUuid)
            .manufacturerUuid(UUID.randomUUID())
            .hubUuid(UUID.randomUUID())
            .name("Sample Product")
            .description("Sample description")
            .stock(50)
            .price(100)
            .build();

        // ProductRepository의 findById 메서드 모킹
        when(productRepository.findById(productUuid)).thenReturn(product);

        // When
        Product resultProduct = productService.getProduct(productUuid);

        // Then
        assertEquals(productUuid, resultProduct.getUuid());
        assertEquals("Sample Product", resultProduct.getName());
        assertEquals("Sample description", resultProduct.getDescription());
        assertEquals(50, resultProduct.getStock());
        assertEquals(100, resultProduct.getPrice());
    }

}
