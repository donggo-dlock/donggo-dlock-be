package com.example.base.commentable.domain.exception;

import com.example.base.common.exception.BusinessException;
import org.springframework.http.HttpStatus;

import static com.example.base.common.domain.BusinessCode.CONFLICT;

public class DuplicateRecommendationException extends BusinessException {

    public DuplicateRecommendationException() {
        super("한 게시물에 대해 추천/비추천은 하루 한 번만 가능합니다.");
    }

    @Override
    public HttpStatus getHttpStatus() {
        return CONFLICT.getHttpStatus();
    }

    @Override
    public String getErrorCode() {
        return CONFLICT.getCode();
    }

    @Override
    public boolean isNecessaryToLog() {
        return false;
    }
}
