package com.phoenix.logistics.core.hub.domain;

import org.springframework.stereotype.Service;

@Service
public class HubService {

    private final HubRegister hubRegister;

    public HubService(HubRegister hubRegister) {
        this.hubRegister = hubRegister;
    }

    public HubWithUuid register(Hub hub) {
        return hubRegister.register(hub);
    }

}
