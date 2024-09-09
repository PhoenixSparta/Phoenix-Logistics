package com.phoenix.logistics.storage.db.core.hub;

import static org.assertj.core.api.Assertions.assertThat;

import com.phoenix.logistics.storage.db.CoreDbContextTest;
import java.time.Duration;
import java.util.UUID;
import org.junit.jupiter.api.Test;

class HubLinkRepositoryIT extends CoreDbContextTest {

    private final HubLinkRepository hubLinkRepository;

    public HubLinkRepositoryIT(HubLinkRepository hubLinkRepository) {
        this.hubLinkRepository = hubLinkRepository;
    }

    @Test
    void 허브_경로가_추가된_후_허브_경로_ID로_조회돼야_한다() {
        // given
        int sequence = 1;
        UUID sourceHubUuid = UUID.randomUUID();
        UUID destinationHubUuid = UUID.randomUUID();
        Duration duration = Duration.ofHours(1);
        double distance = 43.4;
        HubLinkEntity hubLink = hubLinkRepository
            .save(new HubLinkEntity(sequence, sourceHubUuid, destinationHubUuid, duration, distance));

        // when
        HubLinkEntity addedHubLink = hubLinkRepository.findById(hubLink.getId())
            .orElseThrow(() -> new AssertionError("HubLink not found"));

        // then
        assertThat(addedHubLink).isNotNull();
        assertThat(addedHubLink.getId()).isEqualTo(hubLink.getId());
        assertThat(addedHubLink.getSequence()).isEqualTo(hubLink.getSequence());
        assertThat(addedHubLink.getSourceHubUuid()).isEqualTo(hubLink.getSourceHubUuid());
        assertThat(addedHubLink.getDestinationHubUuid()).isEqualTo(hubLink.getDestinationHubUuid());
        assertThat(addedHubLink.getDuration()).isEqualTo(hubLink.getDuration());
        assertThat(addedHubLink.getDistance()).isEqualTo(hubLink.getDistance());
    }

}