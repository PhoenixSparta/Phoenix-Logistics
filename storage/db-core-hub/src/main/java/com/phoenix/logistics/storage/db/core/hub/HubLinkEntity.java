package com.phoenix.logistics.storage.db.core.hub;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.Duration;
import java.util.UUID;

@Entity
@Table(name = "p_hub_links")
public class HubLinkEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "hub_link_id")
    private Long id;

    @Column(nullable = false)
    private int sequence;

    private UUID sourceHubUuid;

    private UUID destinationHubUuid;

    private Duration duration;

    @Column(columnDefinition = "double precision", nullable = false)
    private double distance;

    public HubLinkEntity() {
    }

    public HubLinkEntity(int sequence, UUID sourceHubUuid, UUID destinationHubUuid, Duration duration,
            double distance) {
        this.sequence = sequence;
        this.sourceHubUuid = sourceHubUuid;
        this.destinationHubUuid = destinationHubUuid;
        this.duration = duration;
        this.distance = distance;
    }

    public Long getId() {
        return id;
    }

    public int getSequence() {
        return sequence;
    }

    public UUID getSourceHubUuid() {
        return sourceHubUuid;
    }

    public UUID getDestinationHubUuid() {
        return destinationHubUuid;
    }

    public Duration getDuration() {
        return duration;
    }

    public double getDistance() {
        return distance;
    }

}
