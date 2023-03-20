package com.gongdel.blog.search.domain;

import java.util.List;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class Search {

  @Builder
  @Getter
  @ToString
  @EqualsAndHashCode
  public static class Info {

    private final List<Context> context;
    private final int pageSize;
    private final int totalCount;

    @Builder
    @Getter
    @ToString
    @EqualsAndHashCode
    public static class Context {

      /**
       * 블로그 글 제목
       */
      private String title;

      /**
       * 블로그 글 요약
       */
      private String contents;

      /**
       * 블로그 글 URL
       */
      private String url;

      /**
       * 블로그의 이름
       */
      private String blogName;

      /**
       * 검색 시스템에서 추출한 대표 미리보기 이미지 URL
       */
      private String thumbNail;

      /**
       * 블로그 글 작성시간
       */
      private String dateTime;
    }
  }

  @Builder
  @Getter
  @ToString
  @EqualsAndHashCode
  public static class Query {

    private final String keyword;
    private final int page;
    private final int pageSize;
    private final String sort;
  }
}
