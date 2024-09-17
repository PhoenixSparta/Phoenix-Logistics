package com.phoenix.logistics.core.product.domain.service;

import org.springframework.stereotype.Service;

import com.phoenix.logistics.core.product.domain.repository.OrderRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;

}
