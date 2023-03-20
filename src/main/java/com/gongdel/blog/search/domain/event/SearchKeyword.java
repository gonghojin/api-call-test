package com.gongdel.blog.search.domain.event;

import lombok.Value;

@Value(staticConstructor = "of")
public class SearchKeyword {

  String keyword;
}
