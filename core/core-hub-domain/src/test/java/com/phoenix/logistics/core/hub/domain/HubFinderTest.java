package com.phoenix.logistics.core.hub.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.IntStream;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class HubFinderTest {

    @Mock
    private HubRepository hubRepository;

    private HubFinder hubFinder;

    private List<HubResult> hubResults;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        hubFinder = new HubFinder(hubRepository);

        hubResults = IntStream.rangeClosed(1, 15)
            .mapToObj(i -> new HubResult(UUID.randomUUID(), i, "허브" + i, "도시" + i, "주소" + i, 37.0 + i, 127.0 + i,
                    new Timestamp(LocalDateTime.now(), LocalDateTime.now())))
            .toList();
    }

    @Test
    void 커서가_없을_때_첫_페이지_허브_목록을_조회한다() {
        // given
        int limit = 10;
        String sortKey = "createdAt";
        SortDirection sort = SortDirection.ASC;
        Cursor cursor = new Cursor(null, null, null, limit, sortKey, sort);

        List<HubResult> expectedResults = hubResults.subList(0, limit);
        when(hubRepository.find(cursor)).thenReturn(expectedResults);

        // when
        List<HubResult> results = hubFinder.find(cursor);

        // then
        assertThat(results).isNotNull();
        assertThat(results).hasSize(expectedResults.size());
        assertThat(results.getFirst().sequence()).isEqualTo(expectedResults.getFirst().sequence());
        assertThat(results.getLast().sequence()).isEqualTo(expectedResults.getLast().sequence());
    }

    @Test
    void 커서가_있을_때_다음_페이지의_허브_목록을_조회한다() {
        // given
        int limit = 10;
        String sortKey = "createdAt";
        SortDirection sort = SortDirection.ASC;
        String lastSequence = String.valueOf(hubResults.get(10).sequence());
        Cursor cursor = new Cursor(null, null, lastSequence, limit, sortKey, sort);

        List<HubResult> expectedResults = hubResults.subList(10, 15);
        when(hubRepository.find(cursor)).thenReturn(expectedResults);

        // when
        List<HubResult> results = hubFinder.find(cursor);

        // then
        assertThat(results).isNotNull();
        assertThat(results).hasSize(expectedResults.size());
        assertThat(results.getFirst().sequence()).isEqualTo(expectedResults.getFirst().sequence());
        assertThat(results.getLast().sequence()).isEqualTo(expectedResults.getLast().sequence());
    }

    @Test
    void 마지막_페이지_이후_허브_목록을_조회하면_빈_목록이_반환된다() {
        // given
        int limit = 10;
        String sortKey = "createdAt";
        SortDirection sort = SortDirection.ASC;
        String lastSequence = String.valueOf(hubResults.getLast().sequence());
        Cursor cursor = new Cursor(null, null, lastSequence, limit, sortKey, sort);

        when(hubRepository.find(cursor)).thenReturn(List.of());

        // when
        List<HubResult> results = hubFinder.find(cursor);

        // then
        assertThat(results).isEmpty();
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

        List<HubResult> expectedResults = hubResults.stream().filter(hub -> hub.name().contains(query)).toList();
        when(hubRepository.find(cursor)).thenReturn(expectedResults);

        // when
        List<HubResult> results = hubFinder.find(cursor);

        // then
        assertThat(results).isNotNull();
        for (HubResult result : results) {
            assertThat(result.name()).contains(query);
        }
    }

}