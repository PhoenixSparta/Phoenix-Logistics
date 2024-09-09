package com.phoenix.logistics.storage.db.core.hub;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HubLinkRepository extends JpaRepository<HubLinkEntity, Long> {

    Optional<HubLinkEntity> findById(Long id);

}
