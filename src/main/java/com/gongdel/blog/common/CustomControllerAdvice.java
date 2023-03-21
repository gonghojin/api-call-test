package com.gongdel.blog.common;

import com.gongdel.blog.common.dto.CommonResponse;
import com.gongdel.blog.common.dto.exception.BaseException;
import java.util.Collections;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@Slf4j
@ControllerAdvice
public class CustomControllerAdvice {


  @ExceptionHandler(value = MethodArgumentNotValidException.class)
  protected ResponseEntity<CommonResponse<Object>> onMethodArgumentNotValidException(
      MethodArgumentNotValidException e) {
    log.error("handle MethodArgumentNotValidException", e);

    return new ResponseEntity<>(
        CommonResponse.fail(e.getBindingResult(), HttpStatus.BAD_REQUEST.getReasonPhrase()),
        HttpStatus.BAD_REQUEST);
  }

  /**
   * 지원하지 않는 HTTP Method
   */
  @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
  protected ResponseEntity<CommonResponse<Object>> onHttpRequestMethodNotSupportedException(
      HttpRequestMethodNotSupportedException e) {
    log.error("handle HttpRequestMethodNotSupportedException", e);

    return new ResponseEntity<>(
        CommonResponse
            .fail(Collections.emptyMap(), HttpStatus.METHOD_NOT_ALLOWED.getReasonPhrase()),
        HttpStatus.METHOD_NOT_ALLOWED);
  }

  /**
   * 사용자 정의 에러
   */
  @ExceptionHandler(BaseException.class)
  public ResponseEntity<CommonResponse<Object>> handleBadRequest(BaseException e) {
    log.error(e.getMessage(), e);

    return new ResponseEntity<>(
        CommonResponse
            .fail(Collections.emptyMap(), e.getMessage()), HttpStatus.valueOf(e.hashCode()));
  }

  // 예상치 못한 에러
  @ExceptionHandler(Exception.class)
  protected ResponseEntity<CommonResponse<Object>> onException(Exception e) {
    log.error("handle Exception", e);

    return new ResponseEntity<>(
        CommonResponse
            .fail(Collections.emptyMap(), HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase()),
        HttpStatus.INTERNAL_SERVER_ERROR);
  }
}
