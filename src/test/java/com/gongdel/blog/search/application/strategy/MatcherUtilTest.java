package com.gongdel.blog.search.application.strategy;

import com.gongdel.blog.search.domain.Search.Query;
import com.gongdel.blog.search.infrastructure.client.kakao.KakaoKeywordSearch.Request.Sort;
import com.gongdel.blog.search.infrastructure.client.naver.NaverKeywordSearch.Request;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class MatcherUtilTest {

  @DisplayName("카카오 Sort key 를 네이버 Sort key 로 변환")
  @Test
  void convertKakaoSortToNaverSort() {
    // Given
    Query query = Query.builder()
        .sort(Sort.accuracy.name())
        .build();

    // When
    Request.Sort naverSort = MatcherUtils.convertKakaoSortToNaverSort(query);

    // Then
    Assertions.assertThat(naverSort).isEqualTo(Request.Sort.sim);
    Assertions.assertThat(naverSort).isNotEqualTo(Request.Sort.date);
  }
}