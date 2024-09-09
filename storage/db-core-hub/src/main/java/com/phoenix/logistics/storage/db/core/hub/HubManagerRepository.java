package com.phoenix.logistics.storage.db.core.hub;

import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HubManagerRepository extends JpaRepository<HubManagerEntity, UUID> {

    Optional<HubManagerEntity> findByUuid(UUID uuid);

}
