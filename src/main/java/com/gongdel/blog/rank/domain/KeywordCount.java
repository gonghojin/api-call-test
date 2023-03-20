package com.gongdel.blog.rank.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@EqualsAndHashCode
@AllArgsConstructor
@Builder
@ToString
public class KeywordCount {

  private final String keyword;
  private final int count;

  public static KeywordCount of(Rank entity) {
    return new KeywordCount(entity.getKeyword(), entity.getCount());
  }
}
