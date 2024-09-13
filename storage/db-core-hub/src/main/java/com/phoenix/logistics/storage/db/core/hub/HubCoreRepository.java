package com.phoenix.logistics.storage.db.core.hub;

import com.phoenix.logistics.core.hub.domain.Hub;
import com.phoenix.logistics.core.hub.domain.HubRepository;
import com.phoenix.logistics.core.hub.domain.HubWithUuid;
import org.springframework.stereotype.Repository;

@Repository
public class HubCoreRepository implements HubRepository {

    private final HubJpaRepository hubJpaRepository;

    public HubCoreRepository(HubJpaRepository hubJpaRepository) {
        this.hubJpaRepository = hubJpaRepository;
    }

    @Override
    public HubWithUuid add(Hub hub) {
        return hubJpaRepository.save(HubEntity.of(hub)).toHubWithUuid();
    }

}
