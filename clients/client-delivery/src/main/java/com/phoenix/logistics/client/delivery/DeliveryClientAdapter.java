package com.phoenix.logistics.client.delivery;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.phoenix.logistics.client.delivery.dto.CreateDeliveryRequest;
import com.phoenix.logistics.core.product.domain.client.DeliveryClient;

@Component
public class DeliveryClientAdapter implements DeliveryClient {

    private final FeignDeliveryClient feignDeliveryClient;

    @Autowired
    public DeliveryClientAdapter(FeignDeliveryClient feignDeliveryClient) {
        this.feignDeliveryClient = feignDeliveryClient;
    }

    @Override
    public UUID createDelivery(UUID manufacturerUuid, UUID vendorUuid, UUID orderUuid, UUID sourceHubUuid,
            UUID destinationHubUuid) {
        CreateDeliveryRequest createDeliveryRequest = new CreateDeliveryRequest(manufacturerUuid, vendorUuid, orderUuid,
                sourceHubUuid, destinationHubUuid);
        return feignDeliveryClient.createDelivery(createDeliveryRequest);
    }

}
