package com.phoenix.logistics.storage.db.core.delivery;

import com.phoenix.logistics.core.delivery.domain.Delivery;
import com.phoenix.logistics.core.delivery.domain.DeliveryResult;
import com.phoenix.logistics.core.delivery.domain.DeliveryStaff;
import com.phoenix.logistics.core.delivery.domain.DeliveryWithUuid;
import com.phoenix.logistics.core.delivery.domain.Hub;
import com.phoenix.logistics.core.delivery.domain.Order;
import com.phoenix.logistics.core.delivery.domain.Recipient;
import com.phoenix.logistics.core.delivery.domain.Timestamp;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.util.UUID;

@Entity
@Table(name = "p_deliveries")
public class DeliveryEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "delivery_uuid")
    private UUID uuid;

    @Column(nullable = false)
    private UUID orderUuid;

    @Column(nullable = false)
    private UUID manufacturerUuid;

    @Column(nullable = false)
    private UUID vendorUuid;

    @Column(nullable = false)
    private UUID sourceHubUuid;

    @Column(nullable = false)
    private UUID destinationHubUuid;

    private UUID hubDeliveryStaffUuid;

    private UUID companyDeliveryStaffUuid;

    private String fullAddress;

    @Column(length = 100)
    private String recipientName;

    @Column(length = 100)
    private String recipientSlackId;

    public DeliveryEntity() {
    }

    DeliveryEntity(UUID orderUuid, UUID manufacturerUuid, UUID vendorUuid, UUID sourceHubUuid, UUID destinationHubUuid,
            UUID hubDeliveryStaffUuid, UUID companyDeliveryStaffUuid, String fullAddress, String recipientName,
            String recipientSlackId) {
        this.orderUuid = orderUuid;
        this.manufacturerUuid = manufacturerUuid;
        this.vendorUuid = vendorUuid;
        this.sourceHubUuid = sourceHubUuid;
        this.destinationHubUuid = destinationHubUuid;
        this.hubDeliveryStaffUuid = hubDeliveryStaffUuid;
        this.companyDeliveryStaffUuid = companyDeliveryStaffUuid;
        this.fullAddress = fullAddress;
        this.recipientName = recipientName;
        this.recipientSlackId = recipientSlackId;
    }

    public static DeliveryEntity of(Delivery delivery) {
        return new DeliveryEntity(delivery.order().orderUuid(), delivery.order().manufacturerUuid(),
                delivery.order().vendorUuid(), delivery.hub().sourceHubUuid(), delivery.hub().destinationHubUuid(),
                delivery.deliveryStaff().hubDeliveryStaffUuid(), delivery.deliveryStaff().companyDeliveryStaffUuid(),
                delivery.fullAddress(), delivery.recipient().recipientName(), delivery.recipient().recipientSlackId());
    }

    public DeliveryWithUuid toDeliveryWithUuid() {
        return new DeliveryWithUuid(this.uuid,
                new Delivery(new Order(this.orderUuid, this.manufacturerUuid, this.vendorUuid),
                        new Hub(this.sourceHubUuid, this.destinationHubUuid),
                        new DeliveryStaff(this.hubDeliveryStaffUuid, this.companyDeliveryStaffUuid), this.fullAddress,
                        new Recipient(this.recipientName, this.recipientSlackId)));
    }

    public DeliveryResult toDeliveryResult() {
        return new DeliveryResult(
                new DeliveryWithUuid(this.uuid,
                        new Delivery(new Order(this.orderUuid, this.manufacturerUuid, this.vendorUuid),
                                new Hub(this.sourceHubUuid, this.destinationHubUuid),
                                new DeliveryStaff(this.hubDeliveryStaffUuid, this.companyDeliveryStaffUuid),
                                this.fullAddress, new Recipient(this.recipientName, this.recipientSlackId))),
                new Timestamp(this.getCreatedAt(), this.getUpdatedAt()));
    }

    public UUID getUuid() {
        return uuid;
    }

    public UUID getOrderUuid() {
        return orderUuid;
    }

    public UUID getManufacturerUuid() {
        return manufacturerUuid;
    }

    public UUID getVendorUuid() {
        return vendorUuid;
    }

    public UUID getSourceHubUuid() {
        return sourceHubUuid;
    }

    public UUID getDestinationHubUuid() {
        return destinationHubUuid;
    }

    public UUID getHubDeliveryStaffUuid() {
        return hubDeliveryStaffUuid;
    }

    public UUID getCompanyDeliveryStaffUuid() {
        return companyDeliveryStaffUuid;
    }

    public String getFullAddress() {
        return fullAddress;
    }

    public String getRecipientName() {
        return recipientName;
    }

    public String getRecipientSlackId() {
        return recipientSlackId;
    }

}
