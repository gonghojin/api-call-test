package com.gongdel.blog.search.infrastructure.client.kakao;

import com.gongdel.blog.common.dto.exception.ErrorCode;
import com.gongdel.blog.common.dto.exception.InvalidParamException;
import com.gongdel.blog.common.dto.exception.VendorServerException;
import com.gongdel.blog.search.infrastructure.client.kakao.KakaoKeywordSearch.Request;
import com.gongdel.blog.search.infrastructure.client.kakao.KakaoKeywordSearch.Response;
import java.io.IOException;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.web.reactive.function.client.WebClient;

@ExtendWith(MockitoExtension.class)
class KakaoClientTest {

  private static MockWebServer mockWebServer;
  private KakaoClient kakaoClient;

  @BeforeAll
  static void setUp() throws IOException {
    mockWebServer = new MockWebServer();
    mockWebServer.start();
  }

  @AfterAll
  static void tearDown() throws IOException {
    mockWebServer.shutdown();
  }

  @BeforeEach
  void beforeEach() {
    String baseUrl = String.format("http://localhost:%s", mockWebServer.getPort());
    kakaoClient = new KakaoClient(WebClient.create(baseUrl));
  }

  @Test
  @DisplayName("카카오 API 조회 후 변환테스트")
  void search() {
    // Given
    Request request = Request.builder()
        .query("캠핑")
        .build();

    mockWebServer.enqueue(new MockResponse()
        .setBody(KakaoKeywordSearchMockData.toResponseString())
        .addHeader("Content-Type", "application/json"));

    // When
    Response response = kakaoClient.search(request);

    // Then
    Assertions.assertThat(response).isNotNull();
  }

  @DisplayName("카카오 클라이언트 500에러 핸들러 테스트")
  @Test
  void throw500Error() {
    // Given
    Request request = Request.builder()
        .query("캠핑")
        .build();

    mockWebServer.enqueue(new MockResponse()
        .setResponseCode(HttpStatus.INTERNAL_SERVER_ERROR.value()));

    // When
    // Then
    Assertions.assertThatThrownBy(() -> kakaoClient.search(request)).isInstanceOf(
        VendorServerException.class).hasMessage(ErrorCode.VENDOR_SERVER.getErrorMsg());
  }

  @DisplayName("카카오 클라이언트 400에러 핸들러 테스트")
  @Test
  void throw400Eroor() {
    // Given
    Request request = Request.builder()
        .query("캠핑")
        .build();

    mockWebServer.enqueue(new MockResponse()
        .setResponseCode(HttpStatus.BAD_REQUEST.value()));

    // When
    // Then
    Assertions.assertThatThrownBy(() -> kakaoClient.search(request)).isInstanceOf(
        InvalidParamException.class).hasMessage(ErrorCode.COMMON_INVALID_PARAMETER.getErrorMsg());
  }
}