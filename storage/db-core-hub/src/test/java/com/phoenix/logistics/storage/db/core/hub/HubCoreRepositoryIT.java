package com.phoenix.logistics.storage.db.core.hub;

import static org.assertj.core.api.Assertions.assertThat;

import com.phoenix.logistics.core.hub.domain.Hub;
import com.phoenix.logistics.core.hub.domain.HubResult;
import com.phoenix.logistics.core.hub.domain.HubWithUuid;
import com.phoenix.logistics.storage.db.CoreDbContextTest;
import org.junit.jupiter.api.Test;

class HubCoreRepositoryIT extends CoreDbContextTest {

    private final HubCoreRepository hubCoreRepository;

    public HubCoreRepositoryIT(HubCoreRepository hubCoreRepository) {
        this.hubCoreRepository = hubCoreRepository;
    }

    @Test
    void 허브가_정상적으로_추가돼야_한다() {
        // given
        int sequence = 1;
        String name = "서울특별시 센터";
        String city = "서울특별시";
        String fullAddress = "서울특별시 송파구 송파대로 55";
        double latitude = 37.4747005;
        double longitude = 127.123397;

        Hub hub = new Hub(sequence, name, city, fullAddress, latitude, longitude);

        // when
        HubWithUuid result = hubCoreRepository.add(hub);

        // then
        assertThat(result).isNotNull();
        assertThat(result.hubUuid()).isNotNull();
        assertThat(result.sequence()).isEqualTo(sequence);
        assertThat(result.name()).isEqualTo(name);
        assertThat(result.city()).isEqualTo(city);
        assertThat(result.fullAddress()).isEqualTo(fullAddress);
        assertThat(result.latitude()).isEqualTo(latitude);
        assertThat(result.longitude()).isEqualTo(longitude);
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

        HubWithUuid hub = hubCoreRepository.add(new Hub(sequence, name, city, fullAddress, latitude, longitude));

        // when
        HubResult addedHub = hubCoreRepository.read(hub.hubUuid());

        // then
        assertThat(addedHub).isNotNull();
        assertThat(addedHub.hubUuid()).isEqualTo(hub.hubUuid());
        assertThat(addedHub.sequence()).isEqualTo(hub.sequence());
        assertThat(addedHub.name()).isEqualTo(hub.name());
        assertThat(addedHub.city()).isEqualTo(hub.city());
        assertThat(addedHub.fullAddress()).isEqualTo(hub.fullAddress());
        assertThat(addedHub.latitude()).isEqualTo(hub.latitude());
        assertThat(addedHub.longitude()).isEqualTo(hub.longitude());
        assertThat(addedHub.timestamp().createdAt()).isNotNull();
        assertThat(addedHub.timestamp().updatedAt()).isNotNull();
    }

}