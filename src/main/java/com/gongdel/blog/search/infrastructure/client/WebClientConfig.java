package com.gongdel.blog.search.infrastructure.client;

import com.gongdel.blog.search.infrastructure.client.WebClientProperties.KakaoProperties;
import com.gongdel.blog.search.infrastructure.client.kakao.KakaoClient;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
@EnableConfigurationProperties(value = WebClientProperties.class)
public class WebClientConfig {

  @Bean
  @ConditionalOnProperty(prefix = "webclient.kakao", name = "host")
  public KakaoClient webClient(WebClientProperties clientProperties) {
    KakaoProperties kakaoProperties = clientProperties.getKakao();
    WebClient webClient = WebClient.builder()
        .baseUrl(kakaoProperties.getScheme() + "://" + kakaoProperties.getHost())
        .defaultHeader("Authorization", kakaoProperties.getHeaders().get("Authorization"))
        .build();

    return new KakaoClient(webClient);
  }
}
