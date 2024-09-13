package com.phoenix.logistics.core.hub.domain;

import org.springframework.stereotype.Repository;

@Repository
public interface HubRepository {

    HubWithUuid add(Hub hub);

}
