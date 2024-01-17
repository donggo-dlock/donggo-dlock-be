package com.example.base.user.domain.exception;

import com.example.base.common.exception.BusinessException;
import org.springframework.http.HttpStatus;

import static com.example.base.common.domain.BusinessCode.BAD_REQUEST;

public class CertificationCodeNotMatchedException extends BusinessException {

    public CertificationCodeNotMatchedException() {
        super("인증번호가 일치하지 않습니다.");
    }

    @Override
    public HttpStatus getHttpStatus() {
        return BAD_REQUEST.getHttpStatus();
    }

    @Override
    public String getErrorCode() {
        return BAD_REQUEST.getCode();
    }

    @Override
    public boolean isNecessaryToLog() {
        return true;
    }
}
