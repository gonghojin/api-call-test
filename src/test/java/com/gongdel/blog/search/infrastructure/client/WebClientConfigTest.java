package com.gongdel.blog.search.infrastructure.client;

import com.gongdel.blog.search.infrastructure.client.kakao.KakaoClient;
import com.gongdel.blog.search.infrastructure.client.naver.NaverClient;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest(classes = WebClientConfig.class)
@ActiveProfiles("test")
class WebClientConfigTest {

  @Autowired
  WebClientConfig target;


  @DisplayName("WebClientProperties 값으로 카카오 클라이언트가 정상 생성되는지만 확인")
  @Test
  void kakaoClient() {
    KakaoClient kakaoClient = target.kakaoClient(target.webClient());
    Assertions.assertThat(kakaoClient).isNotNull();

  }

  @DisplayName("WebClientProperties 값으로 네이버 클라이언트가 정상 생성되는지만 확인")
  @Test
  void naverClient() {
    NaverClient naverClient = target.naverClient(target.webClient());
    Assertions.assertThat(naverClient).isNotNull();
  }
}