package com.gongdel.blog.common.dto.exception;

public class VendorServerException extends BaseException {

  public VendorServerException(ErrorCode errorCode) {
    super(errorCode);
  }
}
