package com.gongdel.blog.common;

import com.gongdel.blog.common.dto.CommonResponse;
import com.gongdel.blog.common.dto.exception.BaseException;
import java.util.Collections;
import javax.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@Slf4j
@ControllerAdvice
public class CustomControllerAdvice {

  /**
   * 필수 Parameter 체크(keyword)
   */
  @ExceptionHandler(value = MissingServletRequestParameterException.class)
  protected ResponseEntity<CommonResponse<Object>> onMissingServletRequestParameterException(
      MissingServletRequestParameterException e) {
    log.error("handle MissingServletRequestParameterException", e);

    return new ResponseEntity<>(
        CommonResponse.fail(Collections.emptyMap(), e.getMessage()),
        HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(value = ConstraintViolationException.class)
  protected ResponseEntity<CommonResponse<Object>> onConstraintViolationException(
      ConstraintViolationException e) {
    log.error("handle ConstraintViolationException", e);

    return new ResponseEntity<>(
        CommonResponse.fail(Collections.emptyMap(), e.getMessage()),
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
            .fail(Collections.emptyMap(), e.getMessage()),
        HttpStatus.valueOf(e.getErrorCode().getStatus()));
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
