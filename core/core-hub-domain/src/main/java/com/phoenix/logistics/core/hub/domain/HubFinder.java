package com.phoenix.logistics.core.hub.domain;

import java.util.List;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class HubFinder {

    private final HubRepository hubRepository;

    public HubFinder(HubRepository hubRepository) {
        this.hubRepository = hubRepository;
    }

    @Transactional(readOnly = true)
    public List<HubResult> find(Cursor cursor) {
        return hubRepository.find(cursor);
    }

}
