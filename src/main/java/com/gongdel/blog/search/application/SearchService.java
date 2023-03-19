package com.gongdel.blog.search.application;

import com.gongdel.blog.search.domain.Search;
import com.gongdel.blog.search.domain.Search.Info.Context;
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
public class SearchService {

  private final KakaoClient client;

  public Search.Info search(Search.Query query) {
    Request request = Request.builder()
        .query(query.getKeyword())
        .page(query.getPage())
        .sort(Sort.valueOf(query.getSort()))
        .size(query.getPageSize()).build();

    Response search = client.search(request);

    return Search.Info.builder()
        .context(search.getDocuments().stream().map(
            document -> Context.builder()
                .title(document.getTitle())
                .contents(document.getContents())
                .url(document.getUrl())
                .blogName(document.getBlogName())
                .thumbNail(document.getThumbNail())
                .dateTime(document.getDateTime())
                .build()
        ).collect(Collectors.toList()))
        .pageSize(search.getMeta().getPageableCount())
        .totalCount(search.getMeta().getTotalCount())
        .build();
  }
}
