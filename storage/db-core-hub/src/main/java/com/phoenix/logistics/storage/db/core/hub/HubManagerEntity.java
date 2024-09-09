package com.phoenix.logistics.storage.db.core.hub;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.util.UUID;

@Entity
@Table(name = "p_hub_managers")
public class HubManagerEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "hub_manager_uuid")
    private UUID uuid;

    @Column(nullable = false)
    private UUID hubUuid;

    @Column(nullable = false)
    private Long userId;

    @Column(length = 100, nullable = false)
    private String name;

    public HubManagerEntity() {
    }

    public HubManagerEntity(UUID hubUuid, Long userId, String name) {
        this.hubUuid = hubUuid;
        this.userId = userId;
        this.name = name;
    }

    public UUID getUuid() {
        return uuid;
    }

    public UUID getHubUuid() {
        return hubUuid;
    }

    public Long getUserId() {
        return userId;
    }

    public String getName() {
        return name;
    }

}
