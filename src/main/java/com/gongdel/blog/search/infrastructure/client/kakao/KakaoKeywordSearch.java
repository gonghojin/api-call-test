package com.gongdel.blog.search.infrastructure.client.kakao;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.ArrayList;
import java.util.List;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.util.StringUtils;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class KakaoKeywordSearch {

  @Getter
  @ToString
  @JsonInclude(value = NON_NULL)
  @NoArgsConstructor(access = AccessLevel.PRIVATE)
  public static class Request {

    private static final Sort DEFAULT_SORT = Sort.accuracy;
    private static final Integer DEFAULT_PAGE = 1;
    private static final Integer DEFAULT_SIZE = 10;

    /**
     * 검색을 원하는 질의어 특정 블로그 글만 검색하고 싶은 경우, 블로그 url과 검색어를 공백(' ') 구분자로 넣을 수 있음
     */
    private String query;

    /**
     * 결과 문서 정렬 방식, accuracy(정확도순) 또는 recency(최신순), 기본 값 accuracy
     */
    private Sort sort;

    /**
     * 결과 페이지 번호, 1~50 사이의 값, 기본 값 1
     */
    private Integer page;

    /**
     * 한 페이지에 보여질 문서 수, 1~50 사이의 값, 기본 값 10
     */
    private Integer size;

    @Builder
    public Request(String query,
        Sort sort, Integer page, Integer size) {
      setQuery(query);
      setSort(sort);
      setPage(page);
      setSize(size);
    }

    private void setQuery(String query) {
      if (!StringUtils.hasText(query)) {
        throw new IllegalArgumentException("Request query must not be null");
      }
      this.query = query;
    }

    private void setSort(Sort sort) {
      if (sort == null) {
        sort = DEFAULT_SORT;
      }

      this.sort = sort;
    }

    private void setPage(Integer page) {
      if (page == null) {
        page = DEFAULT_PAGE;
      }

      if (page < 1 || page > 50) {
        throw new IllegalArgumentException("Request page is invalid");
      }

      this.page = page;
    }

    private void setSize(Integer size) {
      if (size == null) {
        size = DEFAULT_SIZE;
      }

      if (size < 1 || size > 50) {
        throw new IllegalArgumentException("Request size is invalid");

      }

      this.size = size;
    }


    public enum Sort {
      accuracy("정확도순"),
      recency("최신순");

      private final String description;

      Sort(String description) {
        this.description = description;
      }
    }
  }

  @Getter
  @NoArgsConstructor(access = AccessLevel.PRIVATE)
  public static class Response {

    private List<Document> documents = new ArrayList<>();
    private Meta meta;

    @Getter
    @ToString
    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    public static class Document {

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
      @JsonProperty("blogname")
      private String blogName;

      /**
       * 검색 시스템에서 추출한 대표 미리보기 이미지 URL, 미리보기 크기 및 화질은 변경될 수 있음
       */
      @JsonProperty("thumbnail")
      private String thumbNail;

      /**
       * 블로그 글 작성시간, ISO 8601
       */
      @JsonProperty("datetime")
      private String dateTime;
    }

    @Getter
    @ToString
    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    public static class Meta {

      /**
       * 검색된 문서 수
       */
      @JsonProperty("total_count")
      private Integer totalCount;

      /**
       * total_count 중 노출 가능 문서 수
       */
      @JsonProperty("pageable_count")
      private Integer pageableCount;

      /**
       * 현재 페이지가 마지막 페이지인지 여부, <br> 값이 false면 page를 증가시켜 다음 페이지를 요청할 수 있음
       */
      @JsonProperty("is_end")
      private Boolean isEnd;
    }
  }
}
