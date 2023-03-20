package com.gongdel.blog.search.application.strategy;

import com.gongdel.blog.search.domain.Search;

/**
 * 검색 추상화 클래스
 */
public interface SearchStrategyTemplate {

  /**
   * keyword 로 페이징 및 정렬 조회
   */
  Search.Info search(Search.Query query);
}
