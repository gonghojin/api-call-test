package com.gongdel.blog.search.application.strategy;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.gongdel.blog.search.domain.Search.Info;
import com.gongdel.blog.search.domain.Search.Info.Context;
import com.gongdel.blog.search.domain.Search.Query;
import com.gongdel.blog.search.infrastructure.client.kakao.KakaoClient;
import com.gongdel.blog.search.infrastructure.client.kakao.KakaoKeywordSearch.Request;
import com.gongdel.blog.search.infrastructure.client.kakao.KakaoKeywordSearch.Request.Sort;
import com.gongdel.blog.search.infrastructure.client.kakao.KakaoKeywordSearch.Response;
import com.gongdel.blog.search.infrastructure.client.kakao.KakaoKeywordSearch.Response.Document;
import com.gongdel.blog.search.infrastructure.client.kakao.KakaoKeywordSearchMockData;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class KakaoStrategyImplTest {

  @Mock
  KakaoClient client;

  @InjectMocks
  KakaoStrategyImpl target;

  @Test
  @DisplayName("카카오 키워드 검색 조회")
  void search() throws JsonProcessingException {
    // Given
    int pageSize = 1;
    int page = 2;
    Query query = Query.builder()
        .keyword("캠핑")
        .page(page)
        .sort("accuracy")
        .pageSize(pageSize).build();

    Request request = Request.builder()
        .query(query.getKeyword())
        .page(query.getPage())
        .sort(Sort.accuracy)
        .size(query.getPageSize()).build();

    Response response = KakaoKeywordSearchMockData.toResponse();
    when(client.search(request)).thenReturn(response);

    // When
    Info info = target.search(query);

    // Then
    assertThat(info).isNotNull();

    Context context = info.getContext().get(0);
    Document expectedContext = response.getDocuments().get(0);
    assertThat(context.getTitle()).isEqualTo(expectedContext.getTitle());
    assertThat(context.getContents()).isEqualTo(expectedContext.getContents());
    assertThat(context.getUrl()).isEqualTo(expectedContext.getUrl());
    assertThat(context.getBlogName()).isEqualTo(expectedContext.getBlogName());
    assertThat(context.getThumbNail()).isEqualTo(expectedContext.getThumbNail());
    assertThat(context.getDateTime()).isEqualTo(expectedContext.getDateTime());

    assertThat(info.getPageSize()).isEqualTo(response.getDocuments().size());
    assertThat(info.getTotalCount()).isEqualTo(response.getMeta().getPageableCount());

  }
}