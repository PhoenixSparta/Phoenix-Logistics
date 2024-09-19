package com.phoenix.logistics.storage.db.core.delivery;

import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DeliveryLogJpaRepository extends JpaRepository<DeliveryLogEntity, UUID> {

    Optional<DeliveryLogEntity> findByUuid(UUID uuid);

}
