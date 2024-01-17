package com.example.base.web.dto;


import lombok.Builder;

@Builder
public record ApiResponse<T>(
        String path,
        String code,
        String message,
        T data
) {
}
