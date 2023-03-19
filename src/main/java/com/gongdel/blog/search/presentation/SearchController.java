package com.gongdel.blog.search.presentation;

import com.gongdel.blog.search.application.SearchService;
import com.gongdel.blog.search.domain.Search.Info;
import com.gongdel.blog.search.domain.Search.Query;
import com.gongdel.common.dto.CommonResponse;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/search")
@RequiredArgsConstructor
public class SearchController {

  private final SearchService searchService;

  @GetMapping
  public CommonResponse<PagingSearchDto> search(
      @RequestParam String keyword, @PageableDefault(page = 1) Pageable pageable) {

    if (!StringUtils.hasText(keyword)) {
      throw new IllegalArgumentException("키워드는 필수 값입니다.");
    }

    Query query = Query.builder()
        .keyword(keyword)
        .pageSize(pageable.getPageSize())
        .sort(pageable.getSort().get().collect(Collectors.toList()).get(0).getProperty())
        .page(pageable.getPageNumber()).build();

    Info info = searchService.search(query);

    PagingSearchDto result = PagingSearchDto.builder()
        .content(info.getContext())
        .pageable(PageRequest.of(info.getPageSize(), info.getTotalCount()))
        .build();

    return CommonResponse.success(result);
  }
}
