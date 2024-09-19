package com.phoenix.logistics.core.order.api.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.phoenix.logistics.core.order.api.controller.dto.CreateOrderRequest;
import com.phoenix.logistics.core.product.domain.model.Order;
import com.phoenix.logistics.core.product.domain.model.ProductOrder;
import com.phoenix.logistics.core.product.domain.service.OrderService;
import com.phoenix.logistics.support.response.ApiResponse;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @PostMapping
    public ApiResponse<Order> createOrder(@RequestBody CreateOrderRequest request) {
        List<ProductOrder> productOrderList = request.productOrderDtoList()
            .stream()
            .map(productOrderDto -> ProductOrder.builder()
                .productUuid(productOrderDto.productUuid())
                .amount(productOrderDto.amount())
                .price(productOrderDto.price())
                .build())
            .toList();

        Order order = orderService.save(request.manufacturerUuid(), request.vendorUuid(), productOrderList);
        return ApiResponse.success(order);
    }

    @GetMapping("/{orderUuid}")
    public ApiResponse<Order> getOrder(@PathVariable(name = "orderUuid") UUID orderUuid) {
        return ApiResponse.success(orderService.getOrder(orderUuid));
    }

}
