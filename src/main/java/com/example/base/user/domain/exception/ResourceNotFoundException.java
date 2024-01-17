package com.example.base.user.domain.exception;

import com.example.base.common.exception.BusinessException;
import org.springframework.http.HttpStatus;

import static com.example.base.common.domain.BusinessCode.NOT_FOUND;

public class ResourceNotFoundException extends BusinessException {

    public ResourceNotFoundException(String datasource, long id) {
        super(datasource + "에서 ID " + id + "를 찾을 수 없습니다.");
    }

    public ResourceNotFoundException(String datasource, String id) {
        super(datasource + "에서 ID " + id + "를 찾을 수 없습니다.");
    }

    @Override
    public HttpStatus getHttpStatus() {
        return NOT_FOUND.getHttpStatus();
    }

    @Override
    public String getErrorCode() {
        return NOT_FOUND.getCode();
    }

    @Override
    public boolean isNecessaryToLog() {
        return false;
    }
}
