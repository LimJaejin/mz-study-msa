package com.lguplus.fleta.data.dto.response;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lguplus.fleta.data.type.response.InnerResponseCodeType;
import com.lguplus.fleta.data.type.response.InnerResponseDataType;
import com.lguplus.fleta.data.type.response.InnerResponseErrorType;
import java.util.List;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.util.ReflectionTestUtils;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * HTTP API 표준 응답 유닛 테스트
 * @version 0.2.0
 */
@DisplayName("HTTP API 응답")
class InnerResponseDtoTest {

    private static final String RESPONSE_SUCCESS_CODE = "0000";
    private static final String RESPONSE_SUCCESS_MESSAGE = "정상";
    private static final String RESPONSE_NO_CONTENT_CODE = "0204";
    private static final String RESPONSE_NO_CONTENT_MESSAGE = "조회 데이터 없음";
    private static final String RESPONSE_BAD_REQUEST_CODE = "0400";
    private static final String RESPONSE_BAD_REQUEST_MESSAGE = "요청 파라미터 오류";
    private static final String RESPONSE_INTERNAL_SERVER_ERROR_CODE = "0500";
    private static final String RESPONSE_INTERNAL_SERVER_ERROR_MESSAGE = "내부 서버 오류";

    ObjectMapper objectMapper = new ObjectMapper();

    @BeforeAll
    static void beforeAll() {
        ReflectionTestUtils.setField(InnerResponseCodeType.OK, "code", RESPONSE_SUCCESS_CODE);
        ReflectionTestUtils.setField(InnerResponseCodeType.OK, "message", RESPONSE_SUCCESS_MESSAGE);
        ReflectionTestUtils.setField(InnerResponseCodeType.OK, "httpStatus", HttpStatus.OK);
        ReflectionTestUtils.setField(InnerResponseCodeType.NO_CONTENT, "code", RESPONSE_NO_CONTENT_CODE);
        ReflectionTestUtils.setField(InnerResponseCodeType.NO_CONTENT, "message", RESPONSE_NO_CONTENT_MESSAGE);
        ReflectionTestUtils.setField(InnerResponseCodeType.NO_CONTENT, "httpStatus", HttpStatus.OK);
        ReflectionTestUtils.setField(InnerResponseCodeType.BAD_REQUEST, "code", RESPONSE_BAD_REQUEST_CODE);
        ReflectionTestUtils.setField(InnerResponseCodeType.BAD_REQUEST, "message", RESPONSE_BAD_REQUEST_MESSAGE);
        ReflectionTestUtils.setField(InnerResponseCodeType.BAD_REQUEST, "httpStatus", HttpStatus.BAD_REQUEST);
        ReflectionTestUtils.setField(InnerResponseCodeType.INTERNAL_SERVER_ERROR, "code", RESPONSE_INTERNAL_SERVER_ERROR_CODE);
        ReflectionTestUtils.setField(InnerResponseCodeType.INTERNAL_SERVER_ERROR, "message", RESPONSE_INTERNAL_SERVER_ERROR_MESSAGE);
        ReflectionTestUtils.setField(InnerResponseCodeType.INTERNAL_SERVER_ERROR, "httpStatus", HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Test
    @DisplayName("Response 객체 - No Result - 응답 코드")
    void basicResponseOfResponseCode() throws JsonProcessingException {
        // When
        InnerResponseDto<?> responseDto = InnerResponseDto.of(InnerResponseCodeType.OK);
        // Then 1
        System.out.println(">>> responseDto = " + responseDto);
        assertThat(responseDto.getCode()).isEqualTo(RESPONSE_SUCCESS_CODE);
        assertThat(responseDto.getMessage()).isEqualTo(RESPONSE_SUCCESS_MESSAGE);
        assertThat(responseDto.getHttpStatus()).isEqualTo(HttpStatus.OK);
        assertThat(responseDto.getResult()).isNull();
        assertThat(responseDto.getErrors()).isNull();
        // Then 2
        String json = objectMapper.writeValueAsString(responseDto);
        System.out.println(">>> json = " + json);
        assertThat(json).contains("code", RESPONSE_SUCCESS_CODE)
            .contains("message", RESPONSE_SUCCESS_MESSAGE)
            .doesNotContain("result")
            .doesNotContain("errors");
    }

    @Test
    @DisplayName("Response 객체 - No Result - HttpStatus")
    void basicResponseOfHttpStatus() throws JsonProcessingException {
        // When
        InnerResponseDto<?> responseDto = InnerResponseDto.of(HttpStatus.OK);
        // Then 1
        System.out.println(">>> responseDto = " + responseDto);
        assertThat(responseDto.getCode()).isEqualTo(RESPONSE_SUCCESS_CODE);
        assertThat(responseDto.getMessage()).isEqualTo(RESPONSE_SUCCESS_MESSAGE);
        assertThat(responseDto.getHttpStatus()).isEqualTo(HttpStatus.OK);
        assertThat(responseDto.getResult()).isNull();
        assertThat(responseDto.getErrors()).isNull();
        // Then 2
        String json = objectMapper.writeValueAsString(responseDto);
        System.out.println(">>> json = " + json);
        assertThat(json).contains("code", RESPONSE_SUCCESS_CODE)
            .contains("message", RESPONSE_SUCCESS_MESSAGE)
            .doesNotContain("result")
            .doesNotContain("errors");
    }

    @Test
    @DisplayName("Response 객체 - SINGLE Result")
    void singleResultResponse() throws JsonProcessingException {
        // Given
        TestDto testDto = new TestDto(1234, "Test Name");
        // When
        InnerResponseDto<TestDto> responseDto = InnerResponseDto.of(InnerResponseCodeType.OK, testDto);
        // Then 1
        System.out.println(">>> responseDto = " + responseDto);
        assertThat(responseDto.getCode()).isEqualTo(RESPONSE_SUCCESS_CODE);
        assertThat(responseDto.getMessage()).isEqualTo(RESPONSE_SUCCESS_MESSAGE);
        assertThat(responseDto.getHttpStatus()).isEqualTo(HttpStatus.OK);
        assertThat(responseDto.getResult()).isNotNull();
        assertThat(responseDto.getResult().getData()).isInstanceOf(TestDto.class);
        assertThat(responseDto.getResult().getDataType()).isEqualTo(InnerResponseDataType.SINGLE);
        assertThat(responseDto.getResult().getDataCount()).isEqualTo(1);
        assertThat(responseDto.getErrors()).isNull();
        // Then 2
        String json = objectMapper.writeValueAsString(responseDto);
        System.out.println(">>> json = " + json);
        assertThat(json).contains("code", RESPONSE_SUCCESS_CODE)
            .contains("message", RESPONSE_SUCCESS_MESSAGE)
            .contains("result", "SINGLE")
            .doesNotContain("errors");
    }

    @Test
    @DisplayName("Response 객체 - LIST Result - 0 Size")
    void listResultResponseZeroSize() throws JsonProcessingException {
        // Given
        List<TestDto> testDtoList = List.of();
        // When
        InnerResponseDto<List<TestDto>> responseDto = InnerResponseDto.of(testDtoList);
        // Then 1
        System.out.println(">>> responseDto = " + responseDto);
        assertThat(responseDto.getCode()).isEqualTo(RESPONSE_NO_CONTENT_CODE);
        assertThat(responseDto.getMessage()).isEqualTo(RESPONSE_NO_CONTENT_MESSAGE);
        assertThat(responseDto.getHttpStatus()).isEqualTo(HttpStatus.OK);
        assertThat(responseDto.getResult()).isNotNull();
        assertThat(responseDto.getResult().getData()).isInstanceOf(List.class);
        assertThat(responseDto.getResult().getDataType()).isEqualTo(InnerResponseDataType.LIST);
        assertThat(responseDto.getResult().getDataCount()).isZero();
        assertThat(responseDto.getErrors()).isNull();
        // Then 2
        String json = objectMapper.writeValueAsString(responseDto);
        System.out.println(">>> json = " + json);
        assertThat(json).contains("code", RESPONSE_NO_CONTENT_CODE)
            .contains("message", RESPONSE_NO_CONTENT_MESSAGE)
            .contains("result", "LIST")
            .doesNotContain("errors");
    }

    @Test
    @DisplayName("ResponseEntity 응답 - No Result")
    void basicResponseEntity() throws JsonProcessingException {
        // When
        ResponseEntity<InnerResponseDto<Object>> responseEntity = InnerResponseDto.toResponseEntity(InnerResponseCodeType.OK);
        // Then 1
        System.out.println(">>> responseEntity = " + responseEntity);
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(responseEntity.getBody()).isNotNull();
        assertThat(responseEntity.getBody().getCode()).isEqualTo(RESPONSE_SUCCESS_CODE);
        assertThat(responseEntity.getBody().getMessage()).isEqualTo(RESPONSE_SUCCESS_MESSAGE);
        assertThat(responseEntity.getBody().getHttpStatus()).isEqualTo(HttpStatus.OK);
        assertThat(responseEntity.getBody().getResult()).isNull();
        assertThat(responseEntity.getBody().getErrors()).isNull();
        // Then 2
        String json = objectMapper.writeValueAsString(responseEntity);
        System.out.println(">>> json = " + json);
        assertThat(json).contains("headers", "body")
            .contains("statusCode", "OK")
            .contains("statusCodeValue", "200")
            .contains("code", RESPONSE_SUCCESS_CODE)
            .contains("message", RESPONSE_SUCCESS_MESSAGE)
            .doesNotContain("result")
            .doesNotContain("errors");
    }

    @Test
    @DisplayName("ResponseEntity 응답 - SINGLE Result")
    void singleResultResponseEntity() throws JsonProcessingException {
        // Given
        TestDto testDto = new TestDto(1234, "Test Name");
        // When
        ResponseEntity<InnerResponseDto<TestDto>> responseEntity = InnerResponseDto.toResponseEntity(testDto);
        // Then 1
        System.out.println(">>> responseEntity = " + responseEntity);
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(responseEntity.getBody()).isNotNull();
        assertThat(responseEntity.getBody().getCode()).isEqualTo(RESPONSE_SUCCESS_CODE);
        assertThat(responseEntity.getBody().getMessage()).isEqualTo(RESPONSE_SUCCESS_MESSAGE);
        assertThat(responseEntity.getBody().getHttpStatus()).isEqualTo(HttpStatus.OK);
        assertThat(responseEntity.getBody().getResult()).isNotNull();
        assertThat(responseEntity.getBody().getResult().getData()).isInstanceOf(TestDto.class);
        assertThat(responseEntity.getBody().getResult().getDataType()).isEqualTo(InnerResponseDataType.SINGLE);
        assertThat(responseEntity.getBody().getResult().getDataCount()).isEqualTo(1);
        assertThat(responseEntity.getBody().getErrors()).isNull();
        // Then 2
        String json = objectMapper.writeValueAsString(responseEntity);
        System.out.println(">>> json = " + json);
        assertThat(json).contains("headers", "body")
            .contains("statusCode", "OK")
            .contains("statusCodeValue", "200")
            .contains("code", RESPONSE_SUCCESS_CODE)
            .contains("message", RESPONSE_SUCCESS_MESSAGE)
            .contains("result", "SINGLE")
            .doesNotContain("errors");
    }

    @Test
    @DisplayName("ResponseEntity 객체 - LIST Result")
    void listResultResponseEntity() throws JsonProcessingException {
        // Given
        TestDto testDto1 = new TestDto(1234, "Test Name 1");
        TestDto testDto2 = new TestDto(5678, "Test Name 2");
        List<TestDto> testDtoList = List.of(testDto1, testDto2);
        // When
        ResponseEntity<InnerResponseDto<List<TestDto>>> responseEntity
            = InnerResponseDto.toResponseEntity(InnerResponseCodeType.OK, testDtoList);
        // Then 1
        System.out.println(">>> responseEntity = " + responseEntity);
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(responseEntity.getBody()).isNotNull();
        assertThat(responseEntity.getBody().getCode()).isEqualTo(RESPONSE_SUCCESS_CODE);
        assertThat(responseEntity.getBody().getMessage()).isEqualTo(RESPONSE_SUCCESS_MESSAGE);
        assertThat(responseEntity.getBody().getHttpStatus()).isEqualTo(HttpStatus.OK);
        assertThat(responseEntity.getBody().getResult()).isNotNull();
        assertThat(responseEntity.getBody().getResult().getData()).isInstanceOf(List.class);
        assertThat(responseEntity.getBody().getResult().getDataType()).isEqualTo(InnerResponseDataType.LIST);
        assertThat(responseEntity.getBody().getResult().getDataCount()).isEqualTo(testDtoList.size());
        assertThat(responseEntity.getBody().getErrors()).isNull();
        // Then 2
        String json = objectMapper.writeValueAsString(responseEntity);
        System.out.println(">>> json = " + json);
        assertThat(json).isNotNull()
            .contains("headers", "body")
            .contains("statusCode", "OK")
            .contains("statusCodeValue", "200")
            .contains("code", RESPONSE_SUCCESS_CODE)
            .contains("message", RESPONSE_SUCCESS_MESSAGE)
            .contains("result", "LIST")
            .doesNotContain("errors");
    }

    @Test
    @DisplayName("Response 객체 - LIST Result 매핑")
    void listResultResponseEntityMapping() throws JsonProcessingException {
        // Given
        TestDto testDto1 = new TestDto(1234, "Test Name 1");
        TestDto testDto2 = new TestDto(5678, "Test Name 2");
        List<TestDto> testDtoList = List.of(testDto1, testDto2);
        // When
        InnerResponseDto<List<TestDto>> responseDto = InnerResponseDto.of(testDtoList);
        // Then 1
        System.out.println(">>> responseDto = " + responseDto);
        assertThat(responseDto.getCode()).isEqualTo(RESPONSE_SUCCESS_CODE);
        assertThat(responseDto.getMessage()).isEqualTo(RESPONSE_SUCCESS_MESSAGE);
        assertThat(responseDto.getHttpStatus()).isEqualTo(HttpStatus.OK);
        assertThat(responseDto.getResult()).isNotNull();
        assertThat(responseDto.getResult().getData()).isInstanceOf(List.class);
        assertThat(responseDto.getResult().getDataType()).isEqualTo(InnerResponseDataType.LIST);
        assertThat(responseDto.getResult().getDataCount()).isEqualTo(testDtoList.size());
        assertThat(responseDto.getErrors()).isNull();
        // Then 2
        String json = objectMapper.writeValueAsString(responseDto);
        InnerResponseDto<List<TestDto>> responseDtoMapping = objectMapper.readValue(json, new TypeReference<>() {});
        System.out.println(">>> responseDtoMapping = " + responseDtoMapping);
        assertThat(responseDtoMapping).isNotNull();
        assertThat(responseDtoMapping.getCode()).isEqualTo(RESPONSE_SUCCESS_CODE);
        assertThat(responseDtoMapping.getMessage()).isEqualTo(RESPONSE_SUCCESS_MESSAGE);
        assertThat(responseDtoMapping.getResult()).isInstanceOf(InnerResponseResultDto.class);
        assertThat(responseDtoMapping.getResult().getData()).hasSize(testDtoList.size());
    }

    @Test
    @DisplayName("ResponseEntity 응답 - Parameter Error")
    void parameterErrorResponseEntity() throws JsonProcessingException {
        // Given
        InnerResponseDto<Object> responseDto = InnerResponseDto.of(InnerResponseCodeType.BAD_REQUEST);
        responseDto.addResponseError(InnerResponseErrorDto.of(InnerResponseErrorType.PARAMETER_ERROR, "첫번째 파라미터가 잘못 되었어요."));
        // When
        ResponseEntity<InnerResponseDto<Object>> responseEntity = responseDto.toResponseEntity();
        // Then 1
        System.out.println(">>> responseEntity = " + responseEntity);
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        assertThat(responseEntity.getBody()).isNotNull();
        assertThat(responseEntity.getBody().getCode()).isEqualTo(RESPONSE_BAD_REQUEST_CODE);
        assertThat(responseEntity.getBody().getMessage()).isEqualTo(RESPONSE_BAD_REQUEST_MESSAGE);
        assertThat(responseEntity.getBody().getHttpStatus()).isEqualTo(HttpStatus.BAD_REQUEST);
        assertThat(responseEntity.getBody().getResult()).isNull();
        assertThat(responseEntity.getBody().getErrors()).isNotNull();
        assertThat(responseEntity.getBody().hasResponseError()).isTrue();
        assertThat(responseEntity.getBody().getResponseErrorSize()).isEqualTo(1);
        // Then 2
        String json = objectMapper.writeValueAsString(responseEntity);
        System.out.println(">>> json = " + json);
        assertThat(json).contains("headers", "body")
            .contains("statusCode", "BAD_REQUEST")
            .contains("statusCodeValue", "400")
            .contains("code", RESPONSE_BAD_REQUEST_CODE)
            .contains("message", RESPONSE_BAD_REQUEST_MESSAGE)
            .doesNotContain("result")
            .contains("errors");
    }

    @Test
    @DisplayName("Response 객체 - Parameter Error 매핑")
    void parameterErrorResponseEntityMapping() throws JsonProcessingException {
        // When
        InnerResponseDto<?> responseDto = InnerResponseDto.of(InnerResponseCodeType.BAD_REQUEST);
        responseDto.addResponseError(InnerResponseErrorDto.of(InnerResponseErrorType.PARAMETER_ERROR, "첫번째 파라미터가 잘못 되었어요."));
        responseDto.addResponseError(InnerResponseErrorDto.of(InnerResponseErrorType.PARAMETER_ERROR));
        // Then 1
        System.out.println(">>> responseDto = " + responseDto);
        assertThat(responseDto.getCode()).isEqualTo(RESPONSE_BAD_REQUEST_CODE);
        assertThat(responseDto.getMessage()).isEqualTo(RESPONSE_BAD_REQUEST_MESSAGE);
        assertThat(responseDto.getHttpStatus()).isEqualTo(HttpStatus.BAD_REQUEST);
        assertThat(responseDto.getResult()).isNull();
        assertThat(responseDto.getErrors()).isNotNull();
        assertThat(responseDto.hasResponseError()).isTrue();
        assertThat(responseDto.getResponseErrorSize()).isEqualTo(2);
        // Then 2
        String json = objectMapper.writeValueAsString(responseDto);
        InnerResponseDto<?> responseDtoMapping = objectMapper.readValue(json, new TypeReference<>() {});
        System.out.println(">>> responseDtoMapping = " + responseDtoMapping);
        assertThat(responseDtoMapping).isNotNull();
        assertThat(responseDtoMapping.getCode()).isEqualTo(RESPONSE_BAD_REQUEST_CODE);
        assertThat(responseDtoMapping.getMessage()).isEqualTo(RESPONSE_BAD_REQUEST_MESSAGE);
        assertThat(responseDtoMapping.getResponseErrorSize()).isEqualTo(responseDto.getResponseErrorSize());
    }

    @Test
    @DisplayName("ResponseEntity 응답 - Server Error")
    void serverErrorResponseEntity() throws JsonProcessingException {
        // When
        InnerResponseErrorDto responseErrorDto1 = InnerResponseErrorDto.of(InnerResponseErrorType.INTERNAL_SERVER_ERROR, "서버에서 알 수 없는 오류가 발생했어요.");
        InnerResponseErrorDto responseErrorDto2 = InnerResponseErrorDto.of(InnerResponseErrorType.PARAMETER_ERROR, "파라미터가 잘못 되었어요.");
        List<InnerResponseErrorDto> responseErrors = List.of(responseErrorDto1, responseErrorDto2);
        InnerResponseDto<Object> responseDto = InnerResponseDto.of(InnerResponseCodeType.INTERNAL_SERVER_ERROR);
        responseDto.addResponseErrors(responseErrors);
        ResponseEntity<InnerResponseDto<Object>> responseEntity = responseDto.toResponseEntity();
        // Then 1
        System.out.println(">>> responseEntity = " + responseEntity);
        assertThat(responseEntity.getBody()).isNotNull();
        assertThat(responseEntity.getBody().getCode()).isEqualTo(RESPONSE_INTERNAL_SERVER_ERROR_CODE);
        assertThat(responseEntity.getBody().getMessage()).isEqualTo(RESPONSE_INTERNAL_SERVER_ERROR_MESSAGE);
        assertThat(responseEntity.getBody().getHttpStatus()).isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR);
        assertThat(responseEntity.getBody().getResult()).isNull();
        assertThat(responseEntity.getBody().getErrors()).isNotNull();
        assertThat(responseEntity.getBody().hasResponseError()).isTrue();
        assertThat(responseEntity.getBody().getResponseErrorSize()).isEqualTo(responseErrors.size());
        // Then 2
        String json = objectMapper.writeValueAsString(responseEntity);
        System.out.println(">>> json = " + json);
        assertThat(json).contains("headers", "body")
            .contains("statusCode", "INTERNAL_SERVER_ERROR")
            .contains("statusCodeValue", "500")
            .contains("code", RESPONSE_INTERNAL_SERVER_ERROR_CODE)
            .contains("message", RESPONSE_INTERNAL_SERVER_ERROR_MESSAGE)
            .doesNotContain("result")
            .contains("errors");
    }

    @Test
    @DisplayName("ResponseEntity 객체 - LIST Result 페이지")
    void listResultWithPageResponseEntity() throws JsonProcessingException {
        // Given
        TestDto testDto1 = new TestDto(1234, "Test Name 1");
        TestDto testDto2 = new TestDto(5678, "Test Name 2");
        List<TestDto> testDtoList = List.of(testDto1, testDto2);
        InnerResponseDto<List<TestDto>> responseDto = InnerResponseDto.of(InnerResponseCodeType.OK, testDtoList);

        // When
        int page = 1;
        int rowSize = 30;
        int pageCount = 15;
        responseDto.getResult().setDataPage(page, rowSize, pageCount);
        ResponseEntity<InnerResponseDto<List<TestDto>>> responseEntity = responseDto.toResponseEntity();
        String json = objectMapper.writeValueAsString(responseEntity);

        // Then 1
        System.out.println(">>> responseEntity = " + responseEntity);
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(responseEntity.getBody()).isNotNull();
        assertThat(responseEntity.getBody().getCode()).isEqualTo(RESPONSE_SUCCESS_CODE);
        assertThat(responseEntity.getBody().getMessage()).isEqualTo(RESPONSE_SUCCESS_MESSAGE);
        assertThat(responseEntity.getBody().getHttpStatus()).isEqualTo(HttpStatus.OK);
        assertThat(responseEntity.getBody().getResult()).isNotNull();
        assertThat(responseEntity.getBody().getResult().getData()).isInstanceOf(List.class);
        assertThat(responseEntity.getBody().getResult().getDataType()).isEqualTo(InnerResponseDataType.LIST);
        assertThat(responseEntity.getBody().getResult().getDataCount()).isEqualTo(testDtoList.size());
        assertThat(responseEntity.getBody().getResult().getDataPage()).isNotNull();
        assertThat(responseEntity.getBody().getResult().getDataPage().getPage()).isEqualTo(page);
        assertThat(responseEntity.getBody().getResult().getDataPage().getRowSize()).isEqualTo(rowSize);
        assertThat(responseEntity.getBody().getResult().getDataPage().getPageCount()).isEqualTo(pageCount);
        assertThat(responseEntity.getBody().getErrors()).isNull();

        // Then 2
        System.out.println(">>> json = " + json);
        assertThat(json).contains("headers", "body")
            .contains("statusCode", "OK")
            .contains("statusCodeValue", "200")
            .contains("code", RESPONSE_SUCCESS_CODE)
            .contains("message", RESPONSE_SUCCESS_MESSAGE)
            .contains("result", "LIST")
            .doesNotContain("errors")
            .contains("dataPage", "page", "rowSize", "pageCount");
    }

    @Test
    @DisplayName("ResponseEntity 객체 - LIST Result 페이지 매핑")
    void listResultWithPageResponseEntityMapping() throws JsonProcessingException {
        // Given
        TestDto testDto1 = new TestDto(1234, "Test Name 1");
        TestDto testDto2 = new TestDto(5678, "Test Name 2");
        List<TestDto> testDtoList = List.of(testDto1, testDto2);
        InnerResponseDto<List<TestDto>> responseDto = InnerResponseDto.of(InnerResponseCodeType.OK, testDtoList);

        // When
        int page = 1;
        int rowSize = 30;
        int pageCount = 15;
        responseDto.getResult().setDataPage(page, rowSize, pageCount);
        System.out.println(">>> responseDto = " + responseDto);
        String json = objectMapper.writeValueAsString(responseDto);
        InnerResponseDto<List<TestDto>> responseDtoMapping = objectMapper.readValue(json, new TypeReference<>() {});

        // Then 1
        System.out.println(">>> responseDto = " + responseDto);
        assertThat(responseDto.getCode()).isEqualTo(RESPONSE_SUCCESS_CODE);
        assertThat(responseDto.getMessage()).isEqualTo(RESPONSE_SUCCESS_MESSAGE);
        assertThat(responseDto.getHttpStatus()).isEqualTo(HttpStatus.OK);
        assertThat(responseDto.getResult()).isNotNull();
        assertThat(responseDto.getResult().getData()).isInstanceOf(List.class);
        assertThat(responseDto.getResult().getDataType()).isEqualTo(InnerResponseDataType.LIST);
        assertThat(responseDto.getResult().getDataCount()).isEqualTo(testDtoList.size());
        assertThat(responseDto.getResult().getDataPage()).isNotNull();
        assertThat(responseDto.getResult().getDataPage().getPage()).isEqualTo(page);
        assertThat(responseDto.getResult().getDataPage().getRowSize()).isEqualTo(rowSize);
        assertThat(responseDto.getResult().getDataPage().getPageCount()).isEqualTo(pageCount);
        assertThat(responseDto.getErrors()).isNull();

        // Then 2
        System.out.println(">>> responseDtoMapping = " + responseDtoMapping);
        assertThat(responseDtoMapping).isNotNull();
        assertThat(responseDtoMapping.getCode()).isEqualTo(RESPONSE_SUCCESS_CODE);
        assertThat(responseDtoMapping.getMessage()).isEqualTo(RESPONSE_SUCCESS_MESSAGE);
        assertThat(responseDtoMapping.getResult()).isInstanceOf(InnerResponseResultDto.class);
        assertThat(responseDtoMapping.getResult().getData()).hasSize(testDtoList.size());
    }

    @Getter
    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    private static class TestDto {

        private int id;
        private String name;
    }
}
