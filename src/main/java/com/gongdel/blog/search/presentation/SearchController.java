package com.gongdel.blog.search.presentation;

import com.gongdel.blog.common.dto.CommonResponse;
import com.gongdel.blog.common.dto.exception.InvalidParamException;
import com.gongdel.blog.search.application.SearchService;
import com.gongdel.blog.search.domain.Search.Info;
import com.gongdel.blog.search.domain.Search.Query;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Order;
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
      throw new InvalidParamException("Keyword is required.");
    }

    String sortValue = extractSortValue(pageable);

    Query query = Query.builder()
        .keyword(keyword)
        .pageSize(pageable.getPageSize())
        .sort(sortValue)
        .page(pageable.getPageNumber()).build();

    Info info = searchService.search(query);

    PagingSearchDto result = PagingSearchDto.builder()
        .content(info.getContext())
        .pageable(PageRequest.of(query.getPage(), info.getPageSize()))
        .total(info.getTotalCount())
        .build();

    return CommonResponse.success(result);
  }

  /**
   * sort 값 있을 시 할당, 없으면 ""
   *
   * @param pageable
   * @return
   */
  private String extractSortValue(Pageable pageable) {
    String sortValue = null;
    if (!pageable.getSort().isEmpty()) {
      List<Order> orders = pageable.getSort().get().collect(Collectors.toList());
      sortValue = orders.get(0).getProperty();
      validationSort(orders);
    }
    return sortValue;
  }

  private void validationSort(List<Order> orders) {
    if (orders.size() >= 2) {
      throw new InvalidParamException("can be no more than 2 sort criteria.");
    }
  }
}
