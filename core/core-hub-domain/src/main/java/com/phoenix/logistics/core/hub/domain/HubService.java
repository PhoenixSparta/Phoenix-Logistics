package com.phoenix.logistics.core.hub.domain;

import java.util.UUID;
import org.springframework.stereotype.Service;

@Service
public class HubService {

    private final HubRegister hubRegister;

    private final HubReader hubReader;

    public HubService(HubRegister hubRegister, HubReader hubReader) {
        this.hubRegister = hubRegister;
        this.hubReader = hubReader;
    }

    public HubWithUuid register(Hub hub) {
        return hubRegister.register(hub);
    }

    public HubResult read(UUID hubUuid) {
        return hubReader.read(hubUuid);
    }

}
