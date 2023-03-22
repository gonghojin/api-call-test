package com.gongdel.blog.search.application.strategy;

import com.gongdel.blog.search.domain.Search.Query;
import com.gongdel.blog.search.infrastructure.client.kakao.KakaoKeywordSearch;
import com.gongdel.blog.search.infrastructure.client.naver.NaverKeywordSearch.Request.Sort;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.util.StringUtils;

/**
 * 카카오와 네이버의 다른 Key 값을 일치시키는 클래스
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class MatcherUtils {

  /**
   * 카카오의 Sort Key를 네이버 Key로 변경
   *
   * @param query 카카오 Sort {@link com.gongdel.blog.search.infrastructure.client.kakao.KakaoKeywordSearch
   * @return 네이버 Sort {@link com.gongdel.blog.search.infrastructure.client.naver.NaverKeywordSearch
   */
  public static Sort convertKakaoSortToNaverSort(Query query) {
    if (!StringUtils.hasText(query.getSort())) {
      return null; // default value 는 Request에서 정의된 기준으로 처리
    }

    return query.getSort().equals(KakaoKeywordSearch.Request.Sort.accuracy.name()) ? Sort.sim
        : Sort.date;
  }
}
