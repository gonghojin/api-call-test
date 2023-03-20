package com.gongdel.blog.search.application.strategy;

import lombok.Getter;
import org.springframework.core.Ordered;

/**
 * 검색 API 우선 적용 정의 인터페이스
 */
public interface SearchStrategy extends SearchStrategyTemplate, Ordered {

  @Getter
  enum Order {
    PRIMARY(0), // 카카오
    SECONDARY(1); // 네이버

    private int orderNumber;

    Order(int orderNumber) {
      this.orderNumber = orderNumber;
    }
  }
}
