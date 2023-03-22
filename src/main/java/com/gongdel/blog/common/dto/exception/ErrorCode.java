package com.gongdel.blog.common.dto.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {

  COMMON_INVALID_PARAMETER(400, "요청한 값이 올바르지 않습니다."),

  VENDOR_SERVER(500, "외부 API에서 일시적인 장애가 있습니다.");

  private final int status;
  private final String errorMsg;

}
