package com.gongdel.blog.search.application;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.gongdel.blog.common.dto.exception.VendorServerException;
import com.gongdel.blog.search.application.strategy.KakaoStrategyImpl;
import com.gongdel.blog.search.application.strategy.NaverStrategyImpl;
import com.gongdel.blog.search.application.strategy.SearchStrategy;
import com.gongdel.blog.search.application.strategy.SearchStrategy.Order;
import com.gongdel.blog.search.domain.Search.Query;
import com.gongdel.blog.search.infrastructure.client.kakao.KakaoClient;
import com.gongdel.blog.search.infrastructure.client.naver.NaverClient;
import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class SearchStrategyExecutorTest {

  @DisplayName("생성자 테스트")
  @Test
  void constructor() throws NoSuchFieldException, IllegalAccessException {
    // Given
    SearchStrategy kakaoSerch = new KakaoStrategyImpl(mock(KakaoClient.class));
    SearchStrategy naverSerch = new NaverStrategyImpl(mock(NaverClient.class));

    // When
    List<SearchStrategy> strategies = List.of(kakaoSerch, naverSerch);
    SearchStrategyExecutor executor = new SearchStrategyExecutor(
        strategies);
    Field privateField
        = SearchStrategyExecutor.class.getDeclaredField("strategyMap");

    privateField.setAccessible(true);

    Map<Integer, SearchStrategy> map = (Map<Integer, SearchStrategy>) privateField.get(executor);

    // Then
    assertThat(map.size()).isEqualTo(strategies.size());
    assertThat(map.get(kakaoSerch.getOrder())).isEqualTo(kakaoSerch);
    assertThat(map.get(naverSerch.getOrder())).isEqualTo(naverSerch);
  }

  @Test
  @DisplayName("카카오 API 가 정상적일 떄는 우선순위 조회")
  void search() {
    SearchStrategy kakao = mock(SearchStrategy.class);
    when(kakao.getOrder()).thenReturn(Order.PRIMARY.getOrderNumber());

    SearchStrategy naver = mock(SearchStrategy.class);
    when(naver.getOrder()).thenReturn(Order.SECONDARY.getOrderNumber());

    SearchStrategyExecutor executor = new SearchStrategyExecutor(
        List.of(kakao, naver));

    executor.search(Query.builder().build());
    verify(kakao, times(1)).search(any());
    verify(naver, times(0)).search(any());
  }

  @Test
  @DisplayName("카카오 API 가 비정상적일 떄는 네이버 API 조회")
  void searchOnKakaoError() {
    SearchStrategy kakao = mock(SearchStrategy.class);
    when(kakao.getOrder()).thenReturn(Order.PRIMARY.getOrderNumber());
    when(kakao.search(any())).thenThrow(VendorServerException.class);

    SearchStrategy naver = mock(SearchStrategy.class);
    when(naver.getOrder()).thenReturn(Order.SECONDARY.getOrderNumber());

    SearchStrategyExecutor executor = new SearchStrategyExecutor(
        List.of(kakao, naver));

    executor.search(Query.builder().build());

    verify(kakao, times(1)).search(any());
    verify(naver, times(1)).search(any());
  }
}