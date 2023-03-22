package com.gongdel.blog.search.infrastructure.client.naver;


import com.gongdel.blog.common.dto.exception.ErrorCode;
import com.gongdel.blog.common.dto.exception.InvalidParamException;
import com.gongdel.blog.common.dto.exception.VendorServerException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
public class NaverClient {

  private final WebClient webClient;

  public NaverKeywordSearch.Response search(NaverKeywordSearch.Request request) {
    return this.webClient.get().uri(builder -> builder
        .path("/v1/search/blog.json")
        .queryParam("query", request.getQuery())
        .queryParam("start", request.getPage())
        .queryParam("display", request.getSize())
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
        .bodyToMono(NaverKeywordSearch.Response.class)
        .block();
  }
}
