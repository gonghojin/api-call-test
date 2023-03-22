package com.gongdel.blog.search.application;

import com.gongdel.blog.common.dto.exception.VendorServerException;
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
 * 카카오 API 장애 시, 네이버 조회로 대체
 */
@Component
@Slf4j
public class SearchStrategyExecutor implements SearchStrategyTemplate {

  private final Map<Integer, SearchStrategy> strategyMap;

  public SearchStrategyExecutor(List<SearchStrategy> strategy) {
    this.strategyMap = strategy.stream()
        .collect(Collectors.toMap(SearchStrategy::getOrder, Function.identity()));

    int length = Order.values().length;
    //의도한 개수와 실제 DI 개수 검증
    if (strategyMap.size() != length) {
      log.warn("Expected {} strategies, but found {}", length, strategyMap.size());
      throw new IllegalStateException("준비된 전략 구현체와 일치하지 않습니다.");
    }
  }

  @Override
  public Info search(Query query) {
    try {
      return strategyMap.get(Order.PRIMARY.getOrderNumber()).search(query);
    } catch (VendorServerException e) {
      log.warn("Error using primary strategy: {}. Using secondary strategy instead.",
          e.getMessage());

      return strategyMap.get(Order.SECONDARY.getOrderNumber()).search(query);
    }
  }
}
