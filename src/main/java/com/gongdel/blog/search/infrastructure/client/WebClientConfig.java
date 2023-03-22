package com.gongdel.blog.search.infrastructure.client;

import com.gongdel.blog.search.infrastructure.client.WebClientProperties.KakaoProperties;
import com.gongdel.blog.search.infrastructure.client.WebClientProperties.NaverProperties;
import com.gongdel.blog.search.infrastructure.client.kakao.KakaoClient;
import com.gongdel.blog.search.infrastructure.client.naver.NaverClient;
import io.netty.channel.ChannelOption;
import io.netty.handler.timeout.ReadTimeoutHandler;
import java.time.Duration;
import java.util.concurrent.TimeUnit;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.netty.http.client.HttpClient;

@Configuration
@RequiredArgsConstructor
@EnableConfigurationProperties(value = WebClientProperties.class)
public class WebClientConfig {

  private final WebClientProperties clientProperties;

  @Bean
  public WebClient webClient() {
    return WebClient.builder()
        .clientConnector(new ReactorClientHttpConnector(httpClient()))
        .build();
  }

  private HttpClient httpClient() {
    return HttpClient.create()
        .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 5000)
        .responseTimeout(Duration.ofMillis(5000))
        .doOnConnected(conn ->
            conn.addHandlerLast(
                new ReadTimeoutHandler(5000, TimeUnit.MILLISECONDS))
        );
  }

  @Bean
  @ConditionalOnProperty(prefix = "webclient.kakao", name = "host")
  public KakaoClient kakaoClient(WebClient defaultWebClient) {
    KakaoProperties kakaoProperties = clientProperties.getKakao();
    WebClient webClient = defaultWebClient.mutate()
        .baseUrl(kakaoProperties.getScheme() + "://" + kakaoProperties.getHost())
        .defaultHeader("Authorization", kakaoProperties.getHeaders().get("Authorization"))
        .clientConnector(new ReactorClientHttpConnector(httpClient()))
        .build();

    return new KakaoClient(webClient);
  }

  @Bean
  @ConditionalOnProperty(prefix = "webclient.naver", name = "host")
  public NaverClient naverClient(WebClient defaultWebClient) {
    NaverProperties naverProperties = clientProperties.getNaver();
    WebClient webClient = defaultWebClient.mutate()
        .baseUrl(naverProperties.getScheme() + "://" + naverProperties.getHost())
        .defaultHeader("X-Naver-Client-Id", naverProperties.getHeaders().get("client-id"))
        .defaultHeader("X-Naver-Client-Secret", naverProperties.getHeaders().get("client-secret"))
        .build();

    return new NaverClient(webClient);
  }
}
