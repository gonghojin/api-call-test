package com.gongdel.blog.rank.application.event;

import com.gongdel.blog.rank.application.RankService;
import com.gongdel.blog.search.domain.event.SearchKeyEvent;
import com.gongdel.blog.search.domain.event.SearchKeyword;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class SearchKeyEventHandler {

  private final RankService rankService;

  @EventListener
  @Async
  public void handleEvent(SearchKeyEvent event) {
    log.debug(String.format("Handle KeyWordEvent - keyword : %s", event.getClass().getName()));

    SearchKeyword searchKeyword = event.getSource();
    rankService.createSearchKeywordHistory(searchKeyword);
  }
}
