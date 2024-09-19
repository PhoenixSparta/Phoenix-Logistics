package com.phoenix.logistics.core.product.domain.service;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.phoenix.logistics.core.product.domain.model.Order;
import com.phoenix.logistics.core.product.domain.model.ProductOrder;
import com.phoenix.logistics.core.product.domain.repository.OrderRepository;
import com.phoenix.logistics.core.product.domain.repository.ProductOrderRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;

    private final ProductOrderRepository productOrderRepository;

    public Order save(UUID manufacturerUuid, UUID vendorUuid, List<ProductOrder> productOrderList) {

        Order createdOrder = orderRepository.save(Order.builder()
            .manufacturerUuid(manufacturerUuid)
            .vendorUuid(vendorUuid)
            .totalPrice(productOrderList.stream().mapToLong(ProductOrder::getPrice).sum())
            .build());

        List<ProductOrder> createdProductOrderList = productOrderRepository.saveAll(productOrderList,
                createdOrder.getUuid());

        createdOrder.setProductOrderList(createdProductOrderList);
        // todo 배달 client 붙이기
        return createdOrder;
    }

    public Order getOrder(UUID orderUuid) {
        Order order = orderRepository.findByOrderUuid(orderUuid);
        List<ProductOrder> productOrderList = productOrderRepository.findByOrderUuid(orderUuid);
        order.setProductOrderList(productOrderList);
        return order;
    }

}
