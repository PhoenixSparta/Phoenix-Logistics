package com.phoenix.logistics.core.hub.api.controller.v1;

import static com.phoenix.logistics.test.api.RestDocsUtils.requestPreprocessor;
import static com.phoenix.logistics.test.api.RestDocsUtils.responsePreprocessor;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.queryParameters;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.phoenix.logistics.core.hub.api.controller.v1.request.HubRegistrationRequest;
import com.phoenix.logistics.core.hub.domain.Cursor;
import com.phoenix.logistics.core.hub.domain.Hub;
import com.phoenix.logistics.core.hub.domain.HubResult;
import com.phoenix.logistics.core.hub.domain.HubService;
import com.phoenix.logistics.core.hub.domain.HubWithUuid;
import com.phoenix.logistics.core.hub.domain.SortDirection;
import com.phoenix.logistics.core.hub.domain.Timestamp;
import com.phoenix.logistics.test.api.RestDocsTest;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.IntStream;
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

    @Test
    void 허브_조회() {
        // given
        int sequence = 1;
        String name = "서울특별시 센터";
        String city = "서울특별시";
        String fullAddress = "서울특별시 송파구 송파대로 55";
        double latitude = 37.4747005;
        double longitude = 127.123397;
        LocalDateTime createdAt = LocalDateTime.now();
        LocalDateTime updatedAt = LocalDateTime.now();
        HubResult hubResult = new HubResult(hubUuid, sequence, name, city, fullAddress, latitude, longitude,
                new Timestamp(createdAt, updatedAt));
        when(hubService.read(any(UUID.class))).thenReturn(hubResult);

        given().contentType("application/json")
            .get("/api/v1/hubs/{hubUuid}", hubUuid.toString())
            .then()
            .status(HttpStatus.OK)
            .apply(document("허브 조회", requestPreprocessor(), responsePreprocessor(),
                    responseFields(fieldWithPath("result").type(JsonFieldType.STRING).description("결과"),
                            fieldWithPath("data.hubUuid").type(JsonFieldType.STRING).description("허브 UUID"),
                            fieldWithPath("data.sequence").type(JsonFieldType.NUMBER).description("허브 순서"),
                            fieldWithPath("data.name").type(JsonFieldType.STRING).description("허브명"),
                            fieldWithPath("data.city").type(JsonFieldType.STRING).description("시도명"),
                            fieldWithPath("data.fullAddress").type(JsonFieldType.STRING).description("허브 주소"),
                            fieldWithPath("data.latitude").type(JsonFieldType.NUMBER).description("위도"),
                            fieldWithPath("data.longitude").type(JsonFieldType.NUMBER).description("경도"),
                            fieldWithPath("data.createdAt").type(JsonFieldType.STRING).description("허브 생성 시간"),
                            fieldWithPath("data.updatedAt").type(JsonFieldType.STRING).description("허브 수정 시간"),
                            fieldWithPath("error").type(JsonFieldType.NULL).ignored())));
    }

    @Test
    void 빈_커서로_허브_목록_조회() {
        List<HubResult> hubResults = List.of(
                new HubResult(UUID.randomUUID(), 1, "허브1", "도시1", "주소1", 37.1, 127.1,
                        new Timestamp(LocalDateTime.now(), LocalDateTime.now())),
                new HubResult(UUID.randomUUID(), 2, "허브2", "도시2", "주소2", 37.2, 127.2,
                        new Timestamp(LocalDateTime.now(), LocalDateTime.now())));
        when(hubService.find(any(Cursor.class))).thenReturn(hubResults);

        given().param("query", "")
            .param("searchBy", "")
            .param("cursor", "")
            .param("limit", 10)
            .param("sortKey", "sequence")
            .param("sort", SortDirection.ASC.name())
            .get("/api/v1/hubs")
            .then()
            .status(HttpStatus.OK)
            .apply(document("허브 목록 조회 (빈 커서)", requestPreprocessor(), responsePreprocessor(),
                    queryParameters(parameterWithName("query").description("검색어").optional(),
                            parameterWithName("searchBy").description("검색 필드 (name / city / fullAddress").optional(),
                            parameterWithName("cursor").description("커서 (첫 페이지: null)").optional(),
                            parameterWithName("limit").description("페이지 크기").optional(),
                            parameterWithName("sortKey").description("정렬 키 (sequence / createdAt / updatedAt)")
                                .optional(),
                            parameterWithName("sort").description("정렬 방향 (ASC / DESC)").optional()),
                    responseFields(fieldWithPath("result").type(JsonFieldType.STRING).description("결과"),
                            fieldWithPath("data.result").type(JsonFieldType.ARRAY).description("허브 목록"),
                            fieldWithPath("data.result[].hubUuid").type(JsonFieldType.STRING).description("허브 UUID"),
                            fieldWithPath("data.result[].sequence").type(JsonFieldType.NUMBER).description("허브 순서"),
                            fieldWithPath("data.result[].name").type(JsonFieldType.STRING).description("허브 이름"),
                            fieldWithPath("data.result[].city").type(JsonFieldType.STRING).description("도시"),
                            fieldWithPath("data.result[].fullAddress").type(JsonFieldType.STRING).description("주소"),
                            fieldWithPath("data.result[].latitude").type(JsonFieldType.NUMBER).description("위도"),
                            fieldWithPath("data.result[].longitude").type(JsonFieldType.NUMBER).description("경도"),
                            fieldWithPath("data.result[].createdAt").type(JsonFieldType.STRING).description("생성 시간"),
                            fieldWithPath("data.result[].updatedAt").type(JsonFieldType.STRING).description("수정 시간"),
                            fieldWithPath("data.hasNext").type(JsonFieldType.BOOLEAN).description("다음 페이지 존재 여부"),
                            fieldWithPath("data.nextCursor").type(JsonFieldType.STRING)
                                .description("다음 페이지 커서")
                                .optional(),
                            fieldWithPath("error").type(JsonFieldType.NULL).ignored())));

        verify(hubService).find(any(Cursor.class));
    }

    @Test
    void 커서로_허브_목록_조회() {
        List<HubResult> hubResults = IntStream.rangeClosed(11, 21)
            .mapToObj(i -> new HubResult(UUID.randomUUID(), i, "허브" + i, "도시" + i, "주소" + i, 37.0 + i, 127.0 + i,
                    new Timestamp(LocalDateTime.now(), LocalDateTime.now())))
            .toList();

        String cursor = "11";
        when(hubService.find(any(Cursor.class))).thenReturn(hubResults);

        given().param("query", "")
            .param("searchBy", "")
            .param("cursor", cursor)
            .param("limit", 10)
            .param("sortKey", "sequence")
            .param("sort", SortDirection.ASC.name())
            .get("/api/v1/hubs")
            .then()
            .status(HttpStatus.OK)
            .apply(document("허브 목록 조회 (커서)", requestPreprocessor(), responsePreprocessor(),
                    queryParameters(parameterWithName("query").description("검색어").optional(),
                            parameterWithName("searchBy").description("검색 필드 (name / city / fullAddress").optional(),
                            parameterWithName("cursor").description("커서 (첫 페이지: null)").optional(),
                            parameterWithName("limit").description("페이지 크기").optional(),
                            parameterWithName("sortKey").description("정렬 키 (sequence / createdAt / updatedAt)")
                                .optional(),
                            parameterWithName("sort").description("정렬 방향 (ASC / DESC)").optional()),
                    responseFields(fieldWithPath("result").type(JsonFieldType.STRING).description("결과"),
                            fieldWithPath("data.result").type(JsonFieldType.ARRAY).description("허브 목록"),
                            fieldWithPath("data.result[].hubUuid").type(JsonFieldType.STRING).description("허브 UUID"),
                            fieldWithPath("data.result[].sequence").type(JsonFieldType.NUMBER).description("허브 순서"),
                            fieldWithPath("data.result[].name").type(JsonFieldType.STRING).description("허브 이름"),
                            fieldWithPath("data.result[].city").type(JsonFieldType.STRING).description("도시"),
                            fieldWithPath("data.result[].fullAddress").type(JsonFieldType.STRING).description("주소"),
                            fieldWithPath("data.result[].latitude").type(JsonFieldType.NUMBER).description("위도"),
                            fieldWithPath("data.result[].longitude").type(JsonFieldType.NUMBER).description("경도"),
                            fieldWithPath("data.result[].createdAt").type(JsonFieldType.STRING).description("생성 시간"),
                            fieldWithPath("data.result[].updatedAt").type(JsonFieldType.STRING).description("수정 시간"),
                            fieldWithPath("data.hasNext").type(JsonFieldType.BOOLEAN).description("다음 페이지 존재 여부"),
                            fieldWithPath("data.nextCursor").type(JsonFieldType.STRING)
                                .description("다음 페이지 커서")
                                .optional(),
                            fieldWithPath("error").type(JsonFieldType.NULL).ignored())));

        verify(hubService).find(any(Cursor.class));
    }

    @Test
    void 허브_검색() {
        String query = "허브1";
        String searchBy = "name";

        List<HubResult> hubResults = List.of(new HubResult(UUID.randomUUID(), 1, "허브1", "도시1", "주소1", 37.1, 127.1,
                new Timestamp(LocalDateTime.now(), LocalDateTime.now())));

        when(hubService.find(any(Cursor.class))).thenReturn(hubResults);

        given().param("query", query)
            .param("searchBy", searchBy)
            .param("cursor", "")
            .param("limit", 10)
            .param("sortKey", "sequence")
            .param("sort", SortDirection.ASC.name())
            .get("/api/v1/hubs")
            .then()
            .status(HttpStatus.OK)
            .apply(document("허브 검색", requestPreprocessor(), responsePreprocessor(),
                    queryParameters(parameterWithName("query").description("검색어").optional(),
                            parameterWithName("searchBy").description("검색 필드 (name / city / fullAddress").optional(),
                            parameterWithName("cursor").description("커서 (첫 페이지: null)").optional(),
                            parameterWithName("limit").description("페이지 크기").optional(),
                            parameterWithName("sortKey").description("정렬 키 (sequence / createdAt / updatedAt)")
                                .optional(),
                            parameterWithName("sort").description("정렬 방향 (ASC / DESC)").optional()),
                    responseFields(fieldWithPath("result").type(JsonFieldType.STRING).description("결과"),
                            fieldWithPath("data.result").type(JsonFieldType.ARRAY).description("허브 목록"),
                            fieldWithPath("data.result[].hubUuid").type(JsonFieldType.STRING).description("허브 UUID"),
                            fieldWithPath("data.result[].sequence").type(JsonFieldType.NUMBER).description("허브 순서"),
                            fieldWithPath("data.result[].name").type(JsonFieldType.STRING).description("허브 이름"),
                            fieldWithPath("data.result[].city").type(JsonFieldType.STRING).description("도시"),
                            fieldWithPath("data.result[].fullAddress").type(JsonFieldType.STRING).description("주소"),
                            fieldWithPath("data.result[].latitude").type(JsonFieldType.NUMBER).description("위도"),
                            fieldWithPath("data.result[].longitude").type(JsonFieldType.NUMBER).description("경도"),
                            fieldWithPath("data.result[].createdAt").type(JsonFieldType.STRING).description("생성 시간"),
                            fieldWithPath("data.result[].updatedAt").type(JsonFieldType.STRING).description("수정 시간"),
                            fieldWithPath("data.hasNext").type(JsonFieldType.BOOLEAN).description("다음 페이지 존재 여부"),
                            fieldWithPath("data.nextCursor").type(JsonFieldType.STRING)
                                .description("다음 페이지 커서")
                                .optional(),
                            fieldWithPath("error").type(JsonFieldType.NULL).ignored())));

        verify(hubService).find(any(Cursor.class));
    }

}