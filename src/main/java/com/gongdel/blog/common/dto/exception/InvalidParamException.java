package com.gongdel.blog.common.dto.exception;

public class InvalidParamException extends BaseException {

    public InvalidParamException(String errorMsg) {
        super(errorMsg, ErrorCode.COMMON_INVALID_PARAMETER);
    }
}
