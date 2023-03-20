package com.gongdel.blog.search.application;

import com.gongdel.blog.search.application.strategy.SearchStrategy;
import com.gongdel.blog.search.application.strategy.SearchStrategy.Order;
import com.gongdel.blog.search.application.strategy.SearchStrategyTemplate;
import com.gongdel.blog.search.domain.Search.Info;
import com.gongdel.blog.search.domain.Search.Query;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * 카카오 블로그 검색 API 장애 시, 네이버 블로그 검색 API 로 제공 - TODO : 네이버 블로그 검색 API 추가 구현 필요
 */
@Component
@Slf4j
public class SearchStrategyExecutor implements SearchStrategyTemplate {

  private final Map<Integer, SearchStrategy> strategyMap;

  public SearchStrategyExecutor(List<SearchStrategy> strategy) {
    this.strategyMap = strategy.stream()
        .collect(Collectors.toMap(SearchStrategy::getOrder, Function.identity()));
  }

  @Override
  public Info search(Query query) {
    try {
      return strategyMap.get(Order.PRIMARY.getOrderNumber()).search(query);
    } catch (IllegalStateException e) {
      if (strategyMap.size() > 1) {
        return strategyMap.get(Order.SECONDARY.getOrderNumber()).search(query);
      }
    }
    throw new IllegalStateException("우회할 API 가 없습니다. 확인이 필요합니다.");
  }
}
