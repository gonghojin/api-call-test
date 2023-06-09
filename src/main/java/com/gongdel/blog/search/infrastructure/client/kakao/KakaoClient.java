package com.gongdel.blog.search.infrastructure.client.kakao;

import com.gongdel.blog.common.dto.exception.ErrorCode;
import com.gongdel.blog.common.dto.exception.InvalidParamException;
import com.gongdel.blog.common.dto.exception.VendorServerException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

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
        .onStatus(HttpStatus::is5xxServerError, server -> {
          return Mono.error(
              new VendorServerException(ErrorCode.VENDOR_SERVER)
          );
        })
        .onStatus(HttpStatus::is4xxClientError, server -> {
          return Mono.error(
              new InvalidParamException(ErrorCode.COMMON_INVALID_PARAMETER)
          );
        })
        .bodyToMono(KakaoKeywordSearch.Response.class)
        .block();
  }
}
