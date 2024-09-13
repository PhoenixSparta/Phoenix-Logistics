package com.phoenix.logistics.storage.db.core.hub;

import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HubJpaRepository extends JpaRepository<HubEntity, UUID> {

    Optional<HubEntity> findByUuid(UUID uuid);

}
