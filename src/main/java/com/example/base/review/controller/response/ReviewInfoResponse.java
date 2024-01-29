package com.example.base.review.controller.response;

import com.example.base.common.service.port.ClockHolder;
import com.example.base.review.domain.Review;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

@Builder
@Schema(description = "리뷰 상세 정보 응답")
public record ReviewInfoResponse(
    Long id,
    String name,
    String content,
    char gender,
    boolean sleepFlag,
    int age,
    String[] result,
    String createdAt
) {
    public static ReviewInfoResponse from(Review review, ClockHolder clockHolder){
        String[] result = review.getResult().split("&");

        return ReviewInfoResponse.builder()
                .id(review.getId())
                .name(review.getName())
                .content(review.getContent())
                .age(review.getAge())
                .gender(review.getGender())
                .sleepFlag(review.getSleepFlag())
                .result(result)
                .createdAt(clockHolder.dateTime(review.getCreatedAt()))
                .build();
    }
}
