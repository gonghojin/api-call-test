package com.gongdel.blog.search.application.strategy;

import com.gongdel.blog.search.domain.Search;
import com.gongdel.blog.search.domain.Search.Info;
import com.gongdel.blog.search.domain.Search.Info.Context;
import com.gongdel.blog.search.domain.Search.Query;
import com.gongdel.blog.search.infrastructure.client.naver.NaverClient;
import com.gongdel.blog.search.infrastructure.client.naver.NaverKeywordSearch.Request;
import com.gongdel.blog.search.infrastructure.client.naver.NaverKeywordSearch.Response;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
@Slf4j
public class NaverStrategyImpl implements SearchStrategy {

  private final NaverClient client;

  @Override
  public Info search(Query query) {
    Request request = Request.builder()
        .query(query.getKeyword())
        .page(query.getPage())
        .sort(MatcherUtils.convertKakaoSortToNaverSort(query))
        .size(query.getPageSize()).build();

    Response response = client.search(request);

    return Search.Info.builder()
        .context(response.getItems().stream().map(
            item -> Context.builder()
                .title(item.getTitle())
                .contents(item.getDescription())
                .url(item.getLink())
                .blogName(item.getBloggerName())
                .thumbNail(item.getBloggerLink())
                .dateTime(item.getDateTime())
                .build()
        ).collect(Collectors.toList()))
        .pageSize(response.getDisplay())
        .totalCount(response.getTotal())
        .build();
  }

  @Override
  public int getOrder() {
    return Order.SECONDARY.getOrderNumber();
  }
}
