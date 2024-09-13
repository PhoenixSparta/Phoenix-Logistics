package com.phoenix.logistics.core.hub.domain;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class HubRegister {

    private final HubRepository hubRepository;

    public HubRegister(HubRepository hubRepository) {
        this.hubRepository = hubRepository;
    }

    @Transactional
    public HubWithUuid register(Hub hub) {
        return hubRepository.add(hub);
    }

}
