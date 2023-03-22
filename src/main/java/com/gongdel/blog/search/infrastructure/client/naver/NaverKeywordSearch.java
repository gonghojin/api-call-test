package com.gongdel.blog.search.infrastructure.client.naver;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.gongdel.blog.common.dto.exception.InvalidParamException;
import java.util.ArrayList;
import java.util.List;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.util.StringUtils;

/**
 * 네이버 검색 요청, 결과 클래스
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class NaverKeywordSearch {

  @Getter
  @ToString
  @EqualsAndHashCode
  @JsonInclude(value = NON_NULL)
  @NoArgsConstructor(access = AccessLevel.PRIVATE)
  public static class Request {

    private static final Sort DEFAULT_SORT = Sort.date;
    private static final Integer DEFAULT_PAGE = 1;
    private static final Integer DEFAULT_SIZE = 10;

    /**
     * 검색어. UTF-8로 인코딩되어야 합니다.
     */
    private String query;

    /**
     * 검색 결과 정렬 방법 - sim: 정확도순으로 내림차순 정렬(기본값) - date: 날짜순으로 내림차순 정렬
     */
    private Sort sort;

    /**
     * 검색 시작 위치(기본값: 1, 최댓값: 1000)
     */
    private Integer page;

    /**
     * 한 번에 표시할 검색 결과 개수(기본값: 10, 최댓값: 100)
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
        throw new InvalidParamException("Request query must not be null");
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
        throw new InvalidParamException("Request page is invalid");
      }

      this.page = page;
    }

    private void setSize(Integer size) {
      if (size == null) {
        size = DEFAULT_SIZE;
      }

      if (size < 1 || size > 50) {
        throw new InvalidParamException("Request size is invalid");

      }

      this.size = size;
    }

    public enum Sort {
      sim("정확도순"), // 정확도순 내림차순
      date("날짜순");

      private final String description;

      Sort(String description) {
        this.description = description;
      }
    }
  }

  @Getter
  @NoArgsConstructor(access = AccessLevel.PRIVATE)
  @EqualsAndHashCode
  public static class Response {

    /**
     * 검색 결과를 생성한 시간
     */
    private String lastBuildDate;

    /**
     * 총 검색 결과 개수
     */
    private Integer total;

    /**
     * 검색 시작 위치
     */
    private Integer start;

    /**
     * 한 번에 표시할 검색 결과 개수
     */
    private Integer display;

    private List<Items> items = new ArrayList<>();

    @Getter
    @ToString
    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    public static class Items {

      /**
       * 블로그 글 제목
       */
      private String title;

      /**
       * 블로그 포스트의 URL
       */
      private String link;

      /**
       * 블로그 포스트의 내용을 요약한 패시지 정보. 패시지 정보에서 검색어와 일치하는 부분은 <b> 태그로 감싸져 있습니다.
       */
      private String description;


      /**
       * 블로그 포스트가 있는 블로그의 이름
       */
      @JsonProperty("bloggername")
      private String bloggerName;

      /**
       * 검색 시스템에서 추출한 대표 미리보기 이미지 URL, 미리보기 크기 및 화질은 변경될 수 있음
       */
      @JsonProperty("bloggerlink")
      private String bloggerLink;

      /**
       * 블로그 포스트가 작성된 날짜
       */
      @JsonProperty("postdate")
      private String dateTime;
    }
  }
}
