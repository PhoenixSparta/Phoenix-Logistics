package com.phoenix.logistics.core.product.domain.service;

import com.phoenix.logistics.core.product.domain.client.DeliveryClient;
import com.phoenix.logistics.core.product.domain.model.DeliveryUuidWithLogUuid;
import com.phoenix.logistics.core.product.domain.model.Order;
import com.phoenix.logistics.core.product.domain.model.ProductOrder;
import com.phoenix.logistics.core.product.domain.repository.OrderRepository;
import com.phoenix.logistics.core.product.domain.repository.ProductOrderRepository;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;

    private final ProductOrderRepository productOrderRepository;

    private final DeliveryClient deliveryClient;

    @Transactional
    public Order save(UUID manufacturerUuid, UUID vendorUuid, String fullAddress, String recipientName,
            String recipientSlackId, List<ProductOrder> productOrderList) {

        Order createdOrder = orderRepository.save(Order.builder()
            .manufacturerUuid(manufacturerUuid)
            .vendorUuid(vendorUuid)
            .totalPrice(productOrderList.stream().mapToLong(ProductOrder::getPrice).sum())
            .build());

        List<ProductOrder> createdProductOrderList = productOrderRepository.saveAll(productOrderList,
                createdOrder.getUuid());

        createdOrder.setProductOrderList(createdProductOrderList);

        // delivery 생성 요청
        DeliveryUuidWithLogUuid deliveryUuidWithLogUuid = deliveryClient.createDelivery(
                createdOrder.getManufacturerUuid(), createdOrder.getVendorUuid(), createdOrder.getUuid(),
                UUID.randomUUID(), UUID.randomUUID(), fullAddress, recipientName, recipientSlackId);
        UUID deliveryUuid = deliveryUuidWithLogUuid.deliveryUuid();
        UUID deliveryLogUuid = deliveryUuidWithLogUuid.deliveryLogUuid();
        // todo to-be 업체 API 통신 후 소속 허브 UUID 받아오기

        // 영속성
        orderRepository.setDelivery(createdOrder.getUuid(), deliveryUuid);

        createdOrder.setDeliveryUuid(deliveryUuid);
        createdOrder.setDeliveryLogUuid(deliveryLogUuid);

        return createdOrder;
    }

    public Order getOrder(UUID orderUuid) {
        Order order = orderRepository.findByOrderUuid(orderUuid);
        List<ProductOrder> productOrderList = productOrderRepository.findByOrderUuid(orderUuid);
        order.setProductOrderList(productOrderList);
        return order;
    }

}
