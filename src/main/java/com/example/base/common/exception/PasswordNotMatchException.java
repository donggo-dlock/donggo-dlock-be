package com.example.base.common.exception;

import org.springframework.http.HttpStatus;

import static com.example.base.common.domain.BusinessCode.FORBIDDEN;

public class PasswordNotMatchException extends BusinessException {

    public PasswordNotMatchException() {
        super("비밀번호가 일치하지 않습니다.");
    }
    @Override
    public HttpStatus getHttpStatus() {
        return FORBIDDEN.getHttpStatus();
    }

    @Override
    public String getErrorCode() {
        return FORBIDDEN.getCode();
    }

    @Override
    public boolean isNecessaryToLog() {
        return false;
    }
}
