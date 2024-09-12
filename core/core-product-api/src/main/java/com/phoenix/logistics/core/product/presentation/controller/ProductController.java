package com.phoenix.logistics.core.product.presentation.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.phoenix.logistics.core.product.application.service.ProductService;
import com.phoenix.logistics.core.product.domain.model.DomainPage;
import com.phoenix.logistics.core.product.domain.model.Product;
import com.phoenix.logistics.core.product.presentation.controller.dto.request.CreateProductRequest;
import com.phoenix.logistics.core.product.presentation.controller.dto.request.SearchProductsRequest;
import com.phoenix.logistics.core.product.support.response.ApiResponse;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @PostMapping
    public ResponseEntity<String> addProduct(@RequestBody CreateProductRequest request) {
        String result = productService.createProduct(request.manufacturerUuid(), request.hubUuid(), request.name(),
                request.price(), request.stock());
        return ResponseEntity.ok(result);
    }

    @GetMapping
    public ApiResponse<DomainPage<Product>> searchProducts(@RequestBody SearchProductsRequest request) {
        return productService.searchProducts(request.searchQuery(), request.sortBy(), request.direction(),
                request.page(), request.pageSize());
    }

}
