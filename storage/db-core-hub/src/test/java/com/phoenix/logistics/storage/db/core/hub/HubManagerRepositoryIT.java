package com.phoenix.logistics.storage.db.core.hub;

import static org.assertj.core.api.Assertions.assertThat;

import com.phoenix.logistics.storage.db.CoreDbContextTest;
import java.util.UUID;
import org.junit.jupiter.api.Test;

class HubManagerRepositoryIT extends CoreDbContextTest {

    private final HubManagerRepository hubManagerRepository;

    public HubManagerRepositoryIT(HubManagerRepository hubManagerRepository) {
        this.hubManagerRepository = hubManagerRepository;
    }

    @Test
    void 허브_관리자가_추가된_후_허브_관리자_UUID로_조회돼야_한다() {
        // given
        UUID hubUuid = UUID.randomUUID();
        Long userId = 1L;
        String name = "홍길동";
        HubManagerEntity hubManager = hubManagerRepository.save(new HubManagerEntity(hubUuid, userId, name));

        // when
        HubManagerEntity addedHubManager = hubManagerRepository.findByUuid(hubManager.getUuid())
            .orElseThrow(() -> new AssertionError("HubManager not found"));

        // then
        assertThat(addedHubManager).isNotNull();
        assertThat(addedHubManager.getUuid()).isEqualTo(hubManager.getUuid());
        assertThat(addedHubManager.getHubUuid()).isEqualTo(hubManager.getHubUuid());
        assertThat(addedHubManager.getUserId()).isEqualTo(hubManager.getUserId());
        assertThat(addedHubManager.getName()).isEqualTo(hubManager.getName());
    }

}