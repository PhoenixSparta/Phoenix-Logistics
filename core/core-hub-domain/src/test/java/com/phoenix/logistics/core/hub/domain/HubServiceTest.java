package com.phoenix.logistics.core.hub.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class HubServiceTest {

    @Mock
    private HubRegister hubRegister;

    private HubService hubService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        hubService = new HubService(hubRegister);
    }

    @Test
    void 허브를_등록한다() {
        // given
        int sequence = 1;
        String name = "서울특별시 센터";
        String city = "서울특별시";
        String fullAddress = "서울특별시 송파구 송파대로 55";
        double latitude = 37.4747005;
        double longitude = 127.123397;
        Hub hub = new Hub(sequence, name, city, fullAddress, latitude, longitude);

        UUID hubUuid = UUID.randomUUID();
        HubWithUuid hubWithUuid = new HubWithUuid(hubUuid, sequence, name, city, fullAddress, latitude, longitude);
        when(hubRegister.register(hub)).thenReturn(hubWithUuid);

        // when
        HubWithUuid result = hubService.register(hub);

        // then
        assertThat(result).isNotNull();
        assertThat(result.hubUuid()).isEqualTo(hubUuid);
        assertThat(result.sequence()).isEqualTo(sequence);
        assertThat(result.name()).isEqualTo(name);
        assertThat(result.city()).isEqualTo(city);
        assertThat(result.fullAddress()).isEqualTo(fullAddress);
        assertThat(result.latitude()).isEqualTo(latitude);
        assertThat(result.longitude()).isEqualTo(longitude);
    }

}