package com.gongdel.blog.common.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor
@Getter
public class CommonResponse<T> {

  private String message;
  private T data;

  private CommonResponse(T data, String message) {
    this.message = message;
    this.data = data;
  }

  public static <T> CommonResponse<T> success(T data) {
    return new CommonResponse<>(data, "");
  }

  public static <T> CommonResponse<T> fail(T data, String message) {
    return new CommonResponse<>(data, message);
  }
}
