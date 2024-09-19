package com.phoenix.logistics.storage.db.core.delivery;

import com.phoenix.logistics.core.delivery.domain.CurrentStatus;
import com.phoenix.logistics.core.delivery.domain.Delivery;
import com.phoenix.logistics.core.delivery.domain.DeliveryLog;
import com.phoenix.logistics.core.delivery.domain.DeliveryLogResult;
import com.phoenix.logistics.core.delivery.domain.DeliveryLogWithUuid;
import com.phoenix.logistics.core.delivery.domain.DeliveryRecord;
import com.phoenix.logistics.core.delivery.domain.DeliveryStaff;
import com.phoenix.logistics.core.delivery.domain.DeliveryStatus;
import com.phoenix.logistics.core.delivery.domain.DeliveryWithUuid;
import com.phoenix.logistics.core.delivery.domain.Hub;
import com.phoenix.logistics.core.delivery.domain.Link;
import com.phoenix.logistics.core.delivery.domain.Order;
import com.phoenix.logistics.core.delivery.domain.Recipient;
import com.phoenix.logistics.core.delivery.domain.Timestamp;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.time.Duration;
import java.util.UUID;

@Entity
@Table(name = "p_delivery_logs")
public class DeliveryLogEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "delivery_log_uuid")
    private UUID uuid;

    @Column
    private int sequence;

    @ManyToOne(fetch = FetchType.LAZY)
    private DeliveryEntity deliveryEntity;

    @Column(nullable = false)
    private UUID orderUuid;

    @Column(nullable = false)
    private UUID sourceHubUuid;

    @Column(nullable = false)
    private UUID destinationHubUuid;

    @Column
    private Duration estimatedDuration;

    @Column(columnDefinition = "double precision")
    private double estimatedDistance;

    @Column
    private Duration actualDuration;

    @Column(columnDefinition = "double precision")
    private double actualDistance;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private CurrentStatus currentStatus;

    public DeliveryLogEntity() {
    }

    DeliveryLogEntity(int sequence, DeliveryEntity deliveryEntity, UUID orderUuid, UUID sourceHubUuid,
            UUID destinationHubUuid, Duration actualDuration, double actualDistance, CurrentStatus currentStatus) {
        this.sequence = sequence;
        this.deliveryEntity = deliveryEntity;
        this.orderUuid = orderUuid;
        this.sourceHubUuid = sourceHubUuid;
        this.destinationHubUuid = destinationHubUuid;
        this.actualDuration = actualDuration;
        this.actualDistance = actualDistance;
        this.currentStatus = currentStatus;
    }

    public static DeliveryLogEntity of(DeliveryLog deliveryLog, DeliveryEntity deliveryEntity) {
        return new DeliveryLogEntity(deliveryLog.sequence(), deliveryEntity,
                deliveryLog.deliveryWithUuid().delivery().order().orderUuid(),
                deliveryLog.deliveryWithUuid().delivery().hub().sourceHubUuid(),
                deliveryLog.deliveryWithUuid().delivery().hub().destinationHubUuid(),
                deliveryLog.link().actualDuration(), deliveryLog.link().actualDistance(),
                deliveryLog.deliveryStatus().currentStatus());
    }

    public DeliveryLogWithUuid toDeliveryLogWithUuid() {
        return new DeliveryLogWithUuid(this.uuid,
                new DeliveryLog(this.sequence,
                        new DeliveryWithUuid(this.deliveryEntity.getUuid(),
                                new Delivery(
                                        new Order(this.orderUuid, this.deliveryEntity.getManufacturerUuid(),
                                                this.deliveryEntity.getVendorUuid()),
                                        new Hub(this.sourceHubUuid, this.destinationHubUuid),
                                        new DeliveryStaff(this.deliveryEntity.getHubDeliveryStaffUuid(),
                                                this.deliveryEntity.getCompanyDeliveryStaffUuid()),
                                        this.deliveryEntity.getFullAddress(),
                                        new Recipient(this.deliveryEntity.getRecipientName(),
                                                this.deliveryEntity.getRecipientSlackId()))),
                        new Link(this.actualDuration, this.actualDistance), new DeliveryStatus(this.currentStatus)));
    }

    public DeliveryLogResult toDeliveryLogResult() {
        return new DeliveryLogResult(
                new DeliveryLogWithUuid(this.uuid, new DeliveryLog(this.sequence,
                        new DeliveryWithUuid(this.deliveryEntity.getUuid(),
                                new Delivery(
                                        new Order(this.orderUuid, this.deliveryEntity.getManufacturerUuid(),
                                                this.deliveryEntity.getVendorUuid()),
                                        new Hub(this.sourceHubUuid, this.destinationHubUuid),
                                        new DeliveryStaff(this.deliveryEntity.getHubDeliveryStaffUuid(),
                                                this.deliveryEntity.getCompanyDeliveryStaffUuid()),
                                        this.deliveryEntity.getFullAddress(),
                                        new Recipient(this.deliveryEntity.getRecipientName(),
                                                this.deliveryEntity.getRecipientSlackId()))),
                        new Link(this.actualDuration, this.actualDistance), new DeliveryStatus(this.currentStatus))),
                new Timestamp(this.getCreatedAt(), this.getUpdatedAt()));
    }

    public DeliveryLogEntity updateCurrentStatus(DeliveryStatus deliveryStatus) {
        this.currentStatus = deliveryStatus.currentStatus();
        return this;
    }

    public DeliveryLogEntity update(DeliveryRecord deliveryRecord) {
        this.actualDuration = deliveryRecord.actualDuration();
        this.actualDistance = deliveryRecord.actualDistance();
        this.currentStatus = deliveryRecord.deliveryStatus().currentStatus();
        return this;
    }

    public UUID getUuid() {
        return uuid;
    }

    public int getSequence() {
        return sequence;
    }

    public DeliveryEntity getDeliveryEntity() {
        return deliveryEntity;
    }

    public UUID getOrderUuid() {
        return orderUuid;
    }

    public UUID getSourceHubUuid() {
        return sourceHubUuid;
    }

    public UUID getDestinationHubUuid() {
        return destinationHubUuid;
    }

    public Duration getEstimatedDuration() {
        return estimatedDuration;
    }

    public double getEstimatedDistance() {
        return estimatedDistance;
    }

    public Duration getActualDuration() {
        return actualDuration;
    }

    public double getActualDistance() {
        return actualDistance;
    }

    public CurrentStatus getCurrentStatus() {
        return currentStatus;
    }

}
