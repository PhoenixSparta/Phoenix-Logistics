package com.phoenix.logistics.core.hub.domain;

import java.util.List;
import java.util.UUID;
import org.springframework.stereotype.Service;

@Service
public class HubService {

    private final HubRegister hubRegister;

    private final HubReader hubReader;

    private final HubFinder hubFinder;

    public HubService(HubRegister hubRegister, HubReader hubReader, HubFinder hubFinder) {
        this.hubRegister = hubRegister;
        this.hubReader = hubReader;
        this.hubFinder = hubFinder;
    }

    public HubWithUuid register(Hub hub) {
        return hubRegister.register(hub);
    }

    public HubResult read(UUID hubUuid) {
        return hubReader.read(hubUuid);
    }

    public List<HubResult> find(Cursor cursor) {
        return hubFinder.find(cursor);
    }

}
