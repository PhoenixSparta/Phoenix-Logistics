package com.phoenix.logistics.core.hub.domain;

import java.util.UUID;
import org.springframework.stereotype.Repository;

@Repository
public interface HubRepository {

    HubWithUuid add(Hub hub);

    HubResult read(UUID hubUuid);

}
