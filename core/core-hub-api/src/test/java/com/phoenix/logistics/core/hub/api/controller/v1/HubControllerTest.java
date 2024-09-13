package com.phoenix.logistics.core.hub.api.controller.v1;

import static com.phoenix.logistics.test.api.RestDocsUtils.requestPreprocessor;
import static com.phoenix.logistics.test.api.RestDocsUtils.responsePreprocessor;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.phoenix.logistics.core.hub.api.controller.v1.request.HubRegistrationRequest;
import com.phoenix.logistics.core.hub.domain.Hub;
import com.phoenix.logistics.core.hub.domain.HubService;
import com.phoenix.logistics.core.hub.domain.HubWithUuid;
import com.phoenix.logistics.test.api.RestDocsTest;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.restdocs.payload.JsonFieldType;

class HubControllerTest extends RestDocsTest {

    private UUID hubUuid;

    private HubService hubService;

    private HubController hubController;

    @BeforeEach
    void setUp() {
        this.hubUuid = UUID.randomUUID();

        hubService = mock(HubService.class);
        hubController = new HubController(hubService);
        mockMvc = mockController(hubController);
    }

    @Test
    void 허브_등록() throws JsonProcessingException {
        // given
        int sequence = 1;
        String name = "서울특별시 센터";
        String city = "서울특별시";
        String fullAddress = "서울특별시 송파구 송파대로 55";
        double latitude = 37.4747005;
        double longitude = 127.123397;
        HubWithUuid hubWithUuid = new HubWithUuid(hubUuid, sequence, name, city, fullAddress, latitude, longitude);
        when(hubService.register(any(Hub.class))).thenReturn(hubWithUuid);

        HubRegistrationRequest request = new HubRegistrationRequest(sequence, name, city, fullAddress, latitude,
                longitude);
        ObjectMapper objectMapper = new ObjectMapper();
        String requestBody = objectMapper.writeValueAsString(request);

        given().contentType("application/json")
            .body(requestBody)
            .post("/api/v1/hubs")
            .then()
            .status(HttpStatus.OK)
            .apply(document("허브 등록", requestPreprocessor(), responsePreprocessor(),
                    requestFields(fieldWithPath("sequence").type(JsonFieldType.NUMBER).description("허브 순서"),
                            fieldWithPath("name").type(JsonFieldType.STRING).description("허브명"),
                            fieldWithPath("city").type(JsonFieldType.STRING).description("시도명"),
                            fieldWithPath("fullAddress").type(JsonFieldType.STRING).description("전체 주소"),
                            fieldWithPath("latitude").type(JsonFieldType.NUMBER).description("위도"),
                            fieldWithPath("longitude").type(JsonFieldType.NUMBER).description("경도")),
                    responseFields(fieldWithPath("result").type(JsonFieldType.STRING).description("결과"),
                            fieldWithPath("data.hubUuid").type(JsonFieldType.STRING).description("허브 UUID"),
                            fieldWithPath("error").type(JsonFieldType.NULL).ignored())));
    }

}