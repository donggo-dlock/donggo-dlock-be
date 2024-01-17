package com.example.base.common.domain;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum BusinessCode {
    //COMMON
    SUCCESS(HttpStatus.OK, "0000","정상"),
    CREATED(HttpStatus.CREATED, "0001","생성됨"),

    //Client
    BAD_REQUEST(HttpStatus.BAD_REQUEST, "C400", "잘못된 요청입니다. 요청내용을 확인하세요."),
    NOT_FOUND(HttpStatus.NOT_FOUND, "C404", "요청내용을 찾을 수 없습니다. 요청내용을 확인하세요."),
    UNAUTHORIZED(HttpStatus.UNAUTHORIZED, "C401", "인증되지 않았습니다. 인증을 확인하세요."),
    FORBIDDEN(HttpStatus.FORBIDDEN,"C403", "권한이 없습니다. 권한을 확인하세요."),

    //Application
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "S500", "시스템 내부오류가 발생했습니다. 담당자에게 문의바랍니다."),
    API_RESPONSE_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "S599","API 응답처리중 오류가 발생했습니다."),


    ;

    private final HttpStatus httpStatus;
    private final String code;
    private final String description;

    BusinessCode(HttpStatus status, String code, String description) {
        this.httpStatus = status;
        this.code = code;
        this.description = description;
    }
}
