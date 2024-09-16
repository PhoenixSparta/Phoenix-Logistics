package com.phoenix.logistics.core.hub.domain;

import java.util.UUID;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class HubReader {

    private final HubRepository hubRepository;

    public HubReader(HubRepository hubRepository) {
        this.hubRepository = hubRepository;
    }

    @Transactional(readOnly = true)
    public HubResult read(UUID hubUuid) {
        return hubRepository.read(hubUuid);
    }

}
