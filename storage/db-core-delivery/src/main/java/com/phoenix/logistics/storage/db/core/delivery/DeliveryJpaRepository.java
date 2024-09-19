package com.phoenix.logistics.storage.db.core.delivery;

import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DeliveryJpaRepository extends JpaRepository<DeliveryEntity, UUID> {

    Optional<DeliveryEntity> findByUuid(UUID uuid);

}
