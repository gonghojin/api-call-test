package com.gongdel.blog.search.domain.event;

import org.springframework.context.ApplicationEvent;

/**
 * 검색 키워드 이벤트
 */
public class SearchKeyEvent extends ApplicationEvent {

  private SearchKeyEvent(SearchKeyword source) {
    super(source);
  }

  public static SearchKeyEvent of(SearchKeyword source) {
    return new SearchKeyEvent(source);
  }

  @Override
  public SearchKeyword getSource() {
    return (SearchKeyword) super.getSource();
  }
}
