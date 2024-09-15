package com.phoenix.logistics.core.order.api.controller;

import org.springframework.web.bind.annotation.RestController;

import com.phoenix.logistics.core.product.domain.service.OrderService;

import lombok.RequiredArgsConstructor;

@RestController("/api/v1/order")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

}
