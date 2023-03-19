package com.gongdel.blog.search.infrastructure.client.kakao;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.gongdel.blog.search.infrastructure.client.kakao.KakaoKeywordSearch.Request;
import com.gongdel.blog.search.infrastructure.client.kakao.KakaoKeywordSearch.Response;
import org.junit.jupiter.api.Test;
import org.springframework.web.reactive.function.client.WebClient;

class KakaoClientTest {

  @Test
  void search() throws JsonProcessingException {
    KakaoClient kakaoClient = new KakaoClient(WebClient.create());
    Response response = kakaoClient.search(Request.builder()
        .query("캠핑")
        .build());

    System.out.println(response);
  }
}