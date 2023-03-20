package com.gongdel.blog.search.application.strategy;

import com.gongdel.blog.search.domain.Search;
import com.gongdel.blog.search.domain.Search.Info;
import com.gongdel.blog.search.domain.Search.Info.Context;
import com.gongdel.blog.search.domain.Search.Query;
import com.gongdel.blog.search.infrastructure.client.kakao.KakaoClient;
import com.gongdel.blog.search.infrastructure.client.kakao.KakaoKeywordSearch.Request;
import com.gongdel.blog.search.infrastructure.client.kakao.KakaoKeywordSearch.Request.Sort;
import com.gongdel.blog.search.infrastructure.client.kakao.KakaoKeywordSearch.Response;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class KakaoStrategyImpl implements SearchStrategy {

  private final KakaoClient client;

  @Override
  public Info search(Query query) {
    Request request = Request.builder()
        .query(query.getKeyword())
        .page(query.getPage())
        .sort(query.getSort() != null ? Sort.valueOf(query.getSort())
            : null) // default value 는 Request에서 정의된 기준으로 처리
        .size(query.getPageSize()).build();

    Response response = client.search(request);

    return Search.Info.builder()
        .context(response.getDocuments().stream().map(
            document -> Context.builder()
                .title(document.getTitle())
                .contents(document.getContents())
                .url(document.getUrl())
                .blogName(document.getBlogName())
                .thumbNail(document.getThumbNail())
                .dateTime(document.getDateTime())
                .build()
        ).collect(Collectors.toList()))
        .pageSize(response.getMeta().getPageableCount())
        .totalCount(response.getMeta().getTotalCount())
        .build();
  }

  @Override
  public int getOrder() {
    return SearchStrategy.Order.PRIMARY.getOrderNumber();
  }
}
