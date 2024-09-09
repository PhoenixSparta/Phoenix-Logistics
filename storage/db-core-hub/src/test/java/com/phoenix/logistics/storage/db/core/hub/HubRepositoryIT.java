package com.phoenix.logistics.storage.db.core.hub;

import static org.assertj.core.api.Assertions.assertThat;

import com.phoenix.logistics.storage.db.CoreDbContextTest;
import org.junit.jupiter.api.Test;

class HubRepositoryIT extends CoreDbContextTest {

    private final HubRepository hubRepository;

    public HubRepositoryIT(HubRepository hubRepository) {
        this.hubRepository = hubRepository;
    }

    @Test
    void 허브가_추가된_후_허브_UUID로_조회돼야_한다() {
        // given
        int sequence = 1;
        String name = "서울특별시 센터";
        String city = "서울특별시";
        String fullAddress = "서울특별시 송파구 송파대로 55";
        double latitude = 37.4747005;
        double longitude = 127.123397;
        HubEntity hub = hubRepository.save(new HubEntity(sequence, name, city, fullAddress, latitude, longitude));

        // when
        HubEntity addedHub = hubRepository.findByUuid(hub.getUuid())
            .orElseThrow(() -> new AssertionError("Hub not found"));

        // then
        assertThat(addedHub).isNotNull();
        assertThat(addedHub.getUuid()).isEqualTo(hub.getUuid());
        assertThat(addedHub.getSequence()).isEqualTo(hub.getSequence());
        assertThat(addedHub.getName()).isEqualTo(hub.getName());
        assertThat(addedHub.getCity()).isEqualTo(hub.getCity());
        assertThat(addedHub.getFullAddress()).isEqualTo(hub.getFullAddress());
        assertThat(addedHub.getLatitude()).isEqualTo(hub.getLatitude());
        assertThat(addedHub.getLongitude()).isEqualTo(hub.getLongitude());
    }

}