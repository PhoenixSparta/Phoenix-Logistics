package com.phoenix.logistics.client.delivery;

import com.phoenix.logistics.client.delivery.dto.CreateDeliveryRequest;
import com.phoenix.logistics.client.delivery.dto.DeliveryResponseData;
import com.phoenix.logistics.core.product.domain.client.DeliveryClient;
import com.phoenix.logistics.core.product.domain.model.DeliveryUuidWithLogUuid;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DeliveryClientAdapter implements DeliveryClient {

    private final FeignDeliveryClient feignDeliveryClient;

    @Autowired
    public DeliveryClientAdapter(FeignDeliveryClient feignDeliveryClient) {
        this.feignDeliveryClient = feignDeliveryClient;
    }

    @Override
    public DeliveryUuidWithLogUuid createDelivery(UUID manufacturerUuid, UUID vendorUuid, UUID orderUuid,
            UUID sourceHubUuid, UUID destinationHubUuid, String fullAddress, String recipientName,
            String recipientSlackId) {
        CreateDeliveryRequest createDeliveryRequest = new CreateDeliveryRequest(manufacturerUuid, vendorUuid, orderUuid,
                sourceHubUuid, destinationHubUuid, fullAddress, recipientName, recipientSlackId);
        DeliveryResponseData deliveryResponseData = feignDeliveryClient.createDelivery(createDeliveryRequest).data();
        return new DeliveryUuidWithLogUuid(deliveryResponseData.deliveryUuid(), deliveryResponseData.deliveryLogUuid());
    }

}
