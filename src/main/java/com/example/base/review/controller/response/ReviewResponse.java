package com.example.base.review.controller.response;

import com.example.base.common.service.port.ClockHolder;
import com.example.base.review.domain.Review;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

@Builder
@Schema(description = "리뷰 상세 정보 응답")
public record ReviewResponse(
        Long id,
        String name,
        int likes,
        int views,
        String createdAt

) {
    public static ReviewResponse from(Review review, ClockHolder clockHolder){
        return ReviewResponse.builder()
                .name(review.getName())
                .id(review.getId())
                .likes(review.getLikes())
                .views(review.getViews())
                .createdAt(clockHolder.dateTime(review.getCreatedAt()))
                .build();
    }
}
