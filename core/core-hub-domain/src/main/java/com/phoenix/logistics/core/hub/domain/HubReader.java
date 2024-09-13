package com.phoenix.logistics.core.hub.domain;

import java.util.UUID;
import org.springframework.stereotype.Component;

@Component
public class HubReader {

    private final HubRepository hubRepository;

    public HubReader(HubRepository hubRepository) {
        this.hubRepository = hubRepository;
    }

    public HubResult read(UUID hubUuid) {
        return hubRepository.read(hubUuid);
    }

}
