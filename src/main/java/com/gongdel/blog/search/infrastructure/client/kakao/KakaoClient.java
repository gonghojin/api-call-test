package com.gongdel.blog.search.infrastructure.client.kakao;


import lombok.RequiredArgsConstructor;
import org.springframework.web.reactive.function.client.WebClient;

@RequiredArgsConstructor
public class KakaoClient {

  private final WebClient webClient;

  public KakaoKeywordSearch.Response search(KakaoKeywordSearch.Request request) {

    return this.webClient.get().uri(builder -> builder
        .path("/v2/search/blog")
        .queryParam("query", request.getQuery())
        .queryParam("page", request.getPage())
        .queryParam("size", request.getSize())
        .queryParam("sort", request.getSort()).build())
        .retrieve()
        .bodyToMono(KakaoKeywordSearch.Response.class)
        .block();
  }
}
