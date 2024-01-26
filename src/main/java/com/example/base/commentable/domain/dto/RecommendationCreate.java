package com.example.base.commentable.domain.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.Builder;

@Builder
@Schema(description = "추천/비추천 생성 요청")
public record RecommendationCreate(
    @Schema(description = "추천/비추천 여부", example = "true")
    @NotNull
    Boolean RecommendationFlag,

    @Schema(description = "추천/비추천 카테고리", allowableValues = {"FOOD","REVIEW"})
    @Pattern(regexp ="^(FOOD|REVIEW)$", message = "Category는 FOOD, REVIEW 중 하나여야 합니다.")
    String Category,

    @Schema(description = "추천/비추천 대상 ID")
    @Min(value = 1, message = "id는 1 이상이어야 합니다.")
    Long id
) {
}
