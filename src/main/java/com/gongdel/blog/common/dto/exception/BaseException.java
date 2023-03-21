package com.gongdel.blog.common.dto.exception;

import lombok.Getter;

@Getter
public class BaseException extends RuntimeException {

    private ErrorCode errorCode;

    public BaseException(ErrorCode errorCode) {
        super(errorCode.getErrorMsg());
        this.errorCode = errorCode;
    }

    public BaseException(String message, ErrorCode errorCode) {
        super(message);
        this.errorCode = errorCode;
    }
}
