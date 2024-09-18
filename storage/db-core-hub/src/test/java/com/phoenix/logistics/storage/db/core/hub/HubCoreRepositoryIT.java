package com.phoenix.logistics.storage.db.core.hub;

import static org.assertj.core.api.Assertions.assertThat;

import com.phoenix.logistics.core.hub.domain.Cursor;
import com.phoenix.logistics.core.hub.domain.Hub;
import com.phoenix.logistics.core.hub.domain.HubResult;
import com.phoenix.logistics.core.hub.domain.HubWithUuid;
import com.phoenix.logistics.core.hub.domain.SortDirection;
import com.phoenix.logistics.storage.db.CoreDbContextTest;
import jakarta.persistence.EntityManager;
import java.util.List;
import java.util.stream.IntStream;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.transaction.annotation.Transactional;

@Transactional
class HubCoreRepositoryIT extends CoreDbContextTest {

    private final HubCoreRepository hubCoreRepository;

    private final EntityManager entityManager;

    private List<HubResult> hubs;

    public HubCoreRepositoryIT(HubCoreRepository hubCoreRepository, EntityManager entityManager) {
        this.hubCoreRepository = hubCoreRepository;
        this.entityManager = entityManager;
    }

    @BeforeEach
    void setUp() {
        hubs = IntStream.rangeClosed(1, 15).mapToObj(i -> {
            int sequence = i;
            String name = "허브" + i;
            String city = "도시" + i;
            String fullAddress = "주소" + i;
            double latitude = 37.4747005 + i;
            double longitude = 127.123397 + i;
            HubEntity hubEntity = new HubEntity(sequence, name, city, fullAddress, latitude, longitude);
            entityManager.persist(hubEntity);
            return hubEntity;
        }).map(HubEntity::toHubResult).toList();

        entityManager.flush();
        entityManager.clear();
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

    @Test
    void 허브_순서_기반_커서로_허브_목록의_첫_번째_페이지를_조회한다() {
        // given
        int limit = 10;
        String sortKey = "createdAt";
        SortDirection sort = SortDirection.ASC;
        Cursor cursor = new Cursor(null, null, null, limit, sortKey, sort);

        // when
        List<HubResult> firstPage = hubCoreRepository.find(cursor);

        // then
        assertThat(firstPage).isNotNull();
        assertThat(firstPage).hasSize(limit);
        assertThat(firstPage.getFirst().sequence()).isEqualTo(hubs.getFirst().sequence());
        assertThat(firstPage.getLast().sequence()).isEqualTo(hubs.get(limit - 1).sequence());
    }

    @Test
    void 허브_순서_기반_커서로_허브_목록의_두_번째_페이지를_조회한다() {
        // given
        int limit = 10;
        String sortKey = "createdAt";
        SortDirection sort = SortDirection.ASC;
        Cursor firstCursor = new Cursor(null, null, null, limit, sortKey, sort);

        // when
        List<HubResult> firstPage = hubCoreRepository.find(firstCursor);
        String lastSequnce = String.valueOf(firstPage.getLast().sequence());
        Cursor secondCursor = new Cursor(null, null, lastSequnce, limit, sortKey, sort);
        List<HubResult> secondPage = hubCoreRepository.find(secondCursor);

        // then
        assertThat(secondPage).isNotNull();
        assertThat(secondPage).hasSize(5);
        assertThat(secondPage.getFirst().sequence()).isEqualTo(hubs.get(10).sequence());
        assertThat(secondPage.getLast().sequence()).isEqualTo(hubs.getLast().sequence());
    }

    @Test
    void 허브_순서_기반_커서로_마지막_허브_이후_조회_시_빈_목록이_반환되어야_한다() {
        // given
        int limit = 10;
        String sortKey = "createdAt";
        SortDirection sort = SortDirection.ASC;
        String lastSequnce = String.valueOf(hubs.getLast().sequence());
        Cursor cursor = new Cursor(null, null, lastSequnce, limit, sortKey, sort);

        // when
        List<HubResult> lastPage = hubCoreRepository.find(cursor);

        // then
        assertThat(lastPage).isEmpty();
    }

    @Test
    void 허브명으로_검색하여_허브_목록을_조회한다() {
        // given
        String query = "허브1";
        String searchBy = "name";
        int limit = 10;
        String sortKey = "createdAt";
        SortDirection sort = SortDirection.ASC;
        Cursor cursor = new Cursor(query, searchBy, null, limit, sortKey, sort);

        // when
        List<HubResult> results = hubCoreRepository.find(cursor);

        // then
        assertThat(results).isNotNull();
        for (HubResult result : results) {
            assertThat(result.name()).contains(query);
        }
    }

}