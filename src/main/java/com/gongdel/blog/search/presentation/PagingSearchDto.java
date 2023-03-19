package com.gongdel.blog.search.presentation;

import com.gongdel.blog.search.domain.Search;
import java.util.List;
import lombok.Builder;
import lombok.Getter;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

@Getter
public class PagingSearchDto extends PageImpl<Search.Info.Context> {

  @Builder
  public PagingSearchDto(List<Search.Info.Context> content, Pageable pageable, long total) {
    super(content, pageable, total);
  }
}
