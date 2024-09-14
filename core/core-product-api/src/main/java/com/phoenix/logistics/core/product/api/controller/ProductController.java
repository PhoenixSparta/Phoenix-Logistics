package com.phoenix.logistics.core.product.api.controller;

import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.phoenix.logistics.core.product.api.controller.dto.request.CreateProductRequest;
import com.phoenix.logistics.core.product.api.controller.dto.request.ModifyProductRequest;
import com.phoenix.logistics.core.product.api.controller.dto.request.SearchProductsRequest;
import com.phoenix.logistics.core.product.domain.model.Product;
import com.phoenix.logistics.core.product.domain.service.ProductService;
import com.phoenix.logistics.support.model.DomainPage;
import com.phoenix.logistics.support.response.ApiResponse;

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
        return ApiResponse.success(productService.searchProducts(request.searchQuery(), request.sortBy(),
                request.direction(), request.page(), request.pageSize()));
    }

    @PatchMapping("/{productUuid}")
    public ApiResponse<Product> modifyProduct(@PathVariable UUID productUuid,
            @RequestBody ModifyProductRequest request) {
        return ApiResponse
            .success(productService.modifyProduct(productUuid, request.name(), request.stock(), request.price()));
    }

}
