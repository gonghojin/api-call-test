package com.gongdel.blog.search.application;

import com.gongdel.blog.search.domain.Search;
import com.gongdel.blog.search.domain.Search.Info;
import com.gongdel.blog.search.domain.event.SearchKeyEvent;
import com.gongdel.blog.search.domain.event.SearchKeyword;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class SearchService {

  private final SearchStrategyExecutor searchStrategyExecutor;
  private final ApplicationEventPublisher publisher;

  public Search.Info search(Search.Query query) {
    Info search = searchStrategyExecutor.search(query);

    publisher.publishEvent(SearchKeyEvent.of(SearchKeyword.of(query.getKeyword())));

    return search;
  }
}
