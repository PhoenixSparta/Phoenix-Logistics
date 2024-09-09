package com.phoenix.logistics.storage.db.core.hub;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.util.UUID;

@Entity
@Table(name = "p_hubs")
public class HubEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "hub_uuid")
    private UUID uuid;

    @Column(nullable = false)
    private int sequence;

    @Column(length = 100, nullable = false)
    private String name;

    @Column(length = 20, nullable = false)
    private String city;

    private String fullAddress;

    @Column(columnDefinition = "double precision", nullable = false)
    private double latitude;

    @Column(columnDefinition = "double precision", nullable = false)
    private double longitude;

    public HubEntity() {
    }

    public HubEntity(int sequence, String name, String city, String fullAddress, double latitude, double longitude) {
        this.sequence = sequence;
        this.name = name;
        this.city = city;
        this.fullAddress = fullAddress;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public UUID getUuid() {
        return uuid;
    }

    public int getSequence() {
        return sequence;
    }

    public String getName() {
        return name;
    }

    public String getCity() {
        return city;
    }

    public String getFullAddress() {
        return fullAddress;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

}
