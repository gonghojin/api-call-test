package com.gongdel.blog.search.presentation;

import com.gongdel.blog.common.dto.CommonResponse;
import com.gongdel.blog.common.dto.exception.InvalidParamException;
import com.gongdel.blog.search.application.SearchService;
import com.gongdel.blog.search.domain.Search.Info;
import com.gongdel.blog.search.domain.Search.Query;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Order;
import org.springframework.util.StringUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/search")
@RequiredArgsConstructor
@Validated
public class SearchController {

  private final SearchService searchService;

  @GetMapping
  public CommonResponse<PagingSearchDto> search(
      @RequestParam @NotBlank String keyword,
      @RequestParam(required = false, defaultValue = "accuracy") String sort,
      @RequestParam(required = false, defaultValue = "1") @Min(1) @Max(50) Integer page,
      @RequestParam(required = false, defaultValue = "10") @Min(1) @Max(50) Integer size
  ) {

    if (!StringUtils.hasText(keyword)) {
      throw new InvalidParamException("Keyword is required.");
    }

    Query query = Query.builder()
        .keyword(keyword)
        .pageSize(size)
        .sort(sort)
        .page(page).build();

    Info info = searchService.search(query);

    PagingSearchDto result = PagingSearchDto.builder()
        .content(info.getContext())
        .pageable(PageRequest.of(query.getPage(), info.getPageSize(), Sort.by(Order.by(sort))))
        .total(info.getTotalCount())
        .build();

    return CommonResponse.success(result);
  }
}
