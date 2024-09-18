package com.phoenix.logistics.core.hub.domain;

import java.util.List;
import java.util.UUID;
import org.springframework.stereotype.Repository;

@Repository
public interface HubRepository {

    HubWithUuid add(Hub hub);

    HubResult read(UUID hubUuid);

    List<HubResult> find(Cursor cursor);

}
