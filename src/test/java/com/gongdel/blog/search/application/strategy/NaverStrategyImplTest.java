package com.gongdel.blog.search.application.strategy;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.gongdel.blog.search.domain.Search.Info;
import com.gongdel.blog.search.domain.Search.Info.Context;
import com.gongdel.blog.search.domain.Search.Query;
import com.gongdel.blog.search.infrastructure.client.kakao.KakaoKeywordSearch;
import com.gongdel.blog.search.infrastructure.client.naver.NaverClient;
import com.gongdel.blog.search.infrastructure.client.naver.NaverKeywordSearch.Request;
import com.gongdel.blog.search.infrastructure.client.naver.NaverKeywordSearch.Request.Sort;
import com.gongdel.blog.search.infrastructure.client.naver.NaverKeywordSearch.Response;
import com.gongdel.blog.search.infrastructure.client.naver.NaverKeywordSearchMockData;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class NaverStrategyImplTest {

  @Mock
  NaverClient client;

  @InjectMocks
  NaverStrategyImpl target;

  @DisplayName("네이버 키워드 검색 조회")
  @Test
  void search() throws JsonProcessingException {
    // Given
    int pageSize = 1;
    int page = 2;
    Query query = Query.builder()
        .keyword("캠핑")
        .page(page)
        .sort(KakaoKeywordSearch.Request.Sort.accuracy.name())
        .pageSize(pageSize).build();

    Request request = Request.builder()
        .size(pageSize)
        .page(page)
        .sort(Sort.sim)
        .query(query.getKeyword())
        .build();

    Response response = NaverKeywordSearchMockData.toResponse();
    when(client.search(request)).thenReturn(response);

    // When
    Info info = target.search(query);

    // Then
    assertThat(info).isNotNull();
    Context context = info.getContext().get(0);
    Context expectedContext = info.getContext().get(0);
    assertThat(context.getTitle()).isEqualTo(expectedContext.getTitle());
    assertThat(context.getContents()).isEqualTo(expectedContext.getContents());
    assertThat(context.getUrl()).isEqualTo(expectedContext.getUrl());
    assertThat(context.getBlogName()).isEqualTo(expectedContext.getBlogName());
    assertThat(context.getThumbNail()).isEqualTo(expectedContext.getThumbNail());
    assertThat(context.getDateTime()).isEqualTo(expectedContext.getDateTime());

    assertThat(info.getPageSize()).isEqualTo(response.getDisplay());
    assertThat(info.getTotalCount()).isEqualTo(response.getTotal());
  }
}