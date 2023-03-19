package com.gongdel.blog.search.infrastructure.client.kakao;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriComponentsBuilder;

@Component
@RequiredArgsConstructor
public class KakaoClient {

  private final WebClient webClient;

  public KakaoKeywordSearch.Response search(KakaoKeywordSearch.Request request) {
    UriComponentsBuilder builder = UriComponentsBuilder
        .fromUriString("https://dapi.kakao.com/v2/search/blog")
        .queryParam("query", request.getQuery()).queryParam("page", request.getPage())
        .queryParam("size", request.getSize()).queryParam("sort", request.getSort());

    return this.webClient.get().uri(builder.build().toUri())
        .header("Authorization", "KakaoAK 829fb1fccd6e657aef1c3f68f336dfed")
        .retrieve()
        .bodyToMono(KakaoKeywordSearch.Response.class)
        .block();
  }
}
