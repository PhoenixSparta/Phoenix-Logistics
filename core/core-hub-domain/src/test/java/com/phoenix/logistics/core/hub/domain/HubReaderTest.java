package com.phoenix.logistics.core.hub.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class HubReaderTest {

    @Mock
    private HubRepository hubRepository;

    private HubReader hubReader;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        hubReader = new HubReader(hubRepository);
    }

    @Test
    void 허브를_조회한다() {
        // given
        int sequence = 1;
        String name = "서울특별시 센터";
        String city = "서울특별시";
        String fullAddress = "서울특별시 송파구 송파대로 55";
        double latitude = 37.4747005;
        double longitude = 127.123397;

        UUID hubUuid = UUID.randomUUID();
        LocalDateTime createdAt = LocalDateTime.now();
        LocalDateTime updatedAt = LocalDateTime.now();
        HubResult hubResult = new HubResult(hubUuid, sequence, name, city, fullAddress, latitude, longitude,
                new Timestamp(createdAt, updatedAt));
        when(hubRepository.read(hubUuid)).thenReturn(hubResult);

        // when
        HubResult result = hubReader.read(hubUuid);

        // then
        assertThat(result).isNotNull();
        assertThat(result.hubUuid()).isEqualTo(hubUuid);
        assertThat(result.sequence()).isEqualTo(sequence);
        assertThat(result.name()).isEqualTo(name);
        assertThat(result.city()).isEqualTo(city);
        assertThat(result.fullAddress()).isEqualTo(fullAddress);
        assertThat(result.latitude()).isEqualTo(latitude);
        assertThat(result.longitude()).isEqualTo(longitude);
        assertThat(result.timestamp().createdAt()).isEqualTo(createdAt);
        assertThat(result.timestamp().updatedAt()).isEqualTo(updatedAt);
    }

}