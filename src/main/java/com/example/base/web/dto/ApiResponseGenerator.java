package com.example.base.web.dto;


import com.example.base.common.domain.BusinessCode;

public class ApiResponseGenerator {

    private ApiResponseGenerator() {
        throw new UnsupportedOperationException("Utility class.");
    }

    /**
     * 기본적인 성공응답
     *
     * @return <code>new ApiResponse<>("0000", "OK");</code>
     */
    public static ApiResponse<Void> success(String path) {
        return ApiResponse.<Void>builder()
                .path(path)
                .code(BusinessCode.SUCCESS.getCode())
                .message(BusinessCode.SUCCESS.getDescription())
                .build();
    }

    /**
     * 성공 응답데이터 제공
     *
     * @param data 응답데이터
     * @return <code>new ApiResponse<>("0000", "OK", data);</code>
     */
    public static <D> ApiResponse<D> success(String path, D data) {
        return ApiResponse.<D>builder()
                .path(path)
                .code(BusinessCode.SUCCESS.getCode())
                .message(BusinessCode.SUCCESS.getDescription())
                .data(data)
                .build();
    }

    /**
     * 데이터 생성 후 성공응답
     *
     * @return <code>new ApiResponse<>("0001", "Created");</code>
     */
    public static ApiResponse<Void> created(String path) {
        return ApiResponse.<Void>builder()
                .path(path)
                .code(BusinessCode.CREATED.getCode())
                .message(BusinessCode.CREATED.getDescription())
                .build();
    }

    /**
     * 기본적인 실패응답
     *
     * @return <code>new ApiResponse<>("5000", "Internal Server Error");</code>
     */
    public static ApiResponse<Void> fail(String path) {
        return ApiResponse.<Void>builder()
                .path(path)
                .code(BusinessCode.INTERNAL_SERVER_ERROR.getCode())
                .message(BusinessCode.INTERNAL_SERVER_ERROR.getDescription())
                .build();
    }

    /**
     * 실패 응답데이터 제공
     *
     * @param data 응답데이터
     * @return <code>new ApiResponse<>("5000", "Internal Server Error", data);</code>
     */
    public static <D> ApiResponse<D> fail(String path, D data) {
        return ApiResponse.<D>builder()
                .path(path)
                .code(BusinessCode.INTERNAL_SERVER_ERROR.getCode())
                .message(BusinessCode.INTERNAL_SERVER_ERROR.getDescription())
                .data(data)
                .build();
    }

    /**
     * 실패응답
     *
     * @param code    실패코드
     * @param message 실패메시지
     * @return <code>new ApiResponse<>(code, message, null);</code>
     */
    public static ApiResponse<Void> fail(String code, String message) {
        return ApiResponse.<Void>builder()
                .code(code)
                .message(message)
                .build();
    }

    /**
     * 응답값 처리(서버에서 응답한 경우는 요청성공(success == true)으로 판단한다)
     *
     * @param code    응답코드
     * @param message 응답메시지
     * @param data    응답데이터
     * @param <D>     응답데이터 클래스
     * @return <code>new ApiResponse<>(code, message, data);</code>
     */
    public static <D> ApiResponse<D> of(String path, String code, String message, D data) {
        return ApiResponse.<D>builder()
                .path(path)
                .code(code)
                .message(message)
                .data(data)
                .build();
    }
}
