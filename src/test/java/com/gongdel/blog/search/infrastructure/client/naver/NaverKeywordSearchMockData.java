package com.gongdel.blog.search.infrastructure.client.naver;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * {@link NaverKeywordSearch} 의 테스트용 mock 데이터
 */
public class NaverKeywordSearchMockData {

  private static ObjectMapper mapper = new ObjectMapper();

  /**
   * {@link NaverKeywordSearch.Response} 의 테스트용 mock 데이터
   *
   * @return 클래스 형태로 제공
   * @throws JsonProcessingException
   */
  public static NaverKeywordSearch.Response toResponse() throws JsonProcessingException {
    return mapper.readValue(toResponseString(), NaverKeywordSearch.Response.class);
  }

  /**
   * {@link NaverKeywordSearch.Response} 의 테스트용 mock 데이터
   *
   * @return 문자 형태로 제공
   * @throws JsonProcessingException
   */
  public static String toResponseString() {
    return "{\n"
        + "    \"lastBuildDate\": \"Tue, 21 Mar 2023 23:37:54 +0900\",\n"
        + "    \"total\": 4013524,\n"
        + "    \"start\": 1,\n"
        + "    \"display\": 1,\n"
        + "    \"items\": [\n"
        + "        {\n"
        + "            \"title\": \"통영 국도 귀신자리(귀신<b>여</b>) 참돔낚시.....가 아닌 감성돔낚시..... \",\n"
        + "            \"link\": \"https://blog.naver.com/dddjjs/223050502803\",\n"
        + "            \"description\": \"우리는 귀신자리(귀신<b>여</b>)에 하선한다는.. 미끼부터 해동시키고.. 우측으로는 땅콩<b>여</b>, 빙장포인트.. 좌측은 약간 홈통처럼 되어있는데.. 너울이 홈통위로 비스듬하게 갯바위를 쳐올릴 때.. 심하게 소리를 낸다고... \",\n"
        + "            \"bloggername\": \"太公望 呂尙&apos;s Angling Diary & Fishing Note\",\n"
        + "            \"bloggerlink\": \"blog.naver.com/dddjjs\",\n"
        + "            \"postdate\": \"20230320\"\n"
        + "        }\n"
        + "    ]\n"
        + "}";
  }
}