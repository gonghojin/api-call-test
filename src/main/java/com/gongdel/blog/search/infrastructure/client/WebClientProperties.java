package com.gongdel.blog.search.infrastructure.client;

import java.util.Map;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.util.StringUtils;

@ConfigurationProperties(prefix = "web-client")
@Setter
@Getter
@NoArgsConstructor
public class WebClientProperties {

  private KakaoProperties kakao;
  private NaverProperties naver;

  @Getter
  @Setter
  @NoArgsConstructor
  public static class KakaoProperties {

    private String host;

    @Getter(AccessLevel.NONE)
    private String scheme;

    private Map<String, String> headers;

    private Integer connectionTimeout;
    private Integer readTimeout;

    public String getScheme() {
      return StringUtils.hasText(scheme) ? scheme : "https";
    }
  }

  @Getter
  @Setter
  @NoArgsConstructor
  public static class NaverProperties {

    private String host;

    @Getter(AccessLevel.NONE)
    private String scheme;

    private Map<String, String> headers;

    public String getScheme() {
      return StringUtils.hasText(scheme) ? scheme : "https";
    }
  }
}
