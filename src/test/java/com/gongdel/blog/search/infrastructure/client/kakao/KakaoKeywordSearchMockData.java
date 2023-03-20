package com.gongdel.blog.search.infrastructure.client.kakao;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * {@link com.gongdel.blog.search.infrastructure.client.kakao.KakaoKeywordSearch} 의 테스트용 mock 데이터
 */
public class KakaoKeywordSearchMockData {

  private static ObjectMapper mapper = new ObjectMapper();

  /**
   * {@link com.gongdel.blog.search.infrastructure.client.kakao.KakaoKeywordSearch.Response} 의 테스트용
   * mock 데이터
   *
   * @return 클래스 형태로 제공
   * @throws JsonProcessingException
   */
  public static KakaoKeywordSearch.Response toResponse() throws JsonProcessingException {
    return mapper.readValue(toResponseString(), KakaoKeywordSearch.Response.class);
  }

  /**
   * {@link com.gongdel.blog.search.infrastructure.client.kakao.KakaoKeywordSearch.Response} 의 테스트용
   * mock 데이터
   *
   * @return 문자 형태로 제공
   * @throws JsonProcessingException
   */
  public static String toResponseString() {
    return "{\n"
        + "    \"documents\": [\n"
        + "        {\n"
        + "            \"blogname\": \"유지니취미생활\",\n"
        + "            \"contents\": \"안녕하세요 #헌터커브 를 구입하게 되었습니다. <b>캠핑</b> 등산 백패킹에 뒤를 이어 바이크에 관심이 생겨서 지인지인의 도움으로 헌터커브를 빠르게 구할 수 있었습니다. ! 헌터커브에 대한 설명은 다른 유투버분들이 이미 워낙 잘 올려주겼고 저는 바이크를 잘 알지도 못하는 바린이라 괜히 아는척하고 어설프게 올리지...\",\n"
        + "            \"datetime\": \"2023-03-20T17:17:30.000+09:00\",\n"
        + "            \"thumbnail\": \"https://xxx\",\n"
        + "            \"title\": \"헌터커브ct125 초보 혼다바이크 연습\",\n"
        + "            \"url\": \"http://xxx\"\n"
        + "        }\n"
        + "    ],\n"
        + "    \"meta\": {\n"
        + "        \"is_end\": false,\n"
        + "        \"pageable_count\": 800,\n"
        + "        \"total_count\": 5019245\n"
        + "    }\n"
        + "}";
  }
}