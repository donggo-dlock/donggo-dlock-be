package com.example.base.food.domain.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.annotation.Nullable;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Pattern;
import lombok.Builder;

@Builder
public record FoodSearch(
        @Schema(description = "검색어, 제목 기준", example = "피자")
        String keyword,

        @Schema(description = "검사 n일전, null 값 가능", example = "3")
        @Max(value = 3, message = "daysBeforeTest는 3일 이하로 설정할 수 있습니다.")
        @Nullable
        int daysBeforeTest,

        @Schema(description = "정렬 타입", example = "RECENT", defaultValue = "RECENT", allowableValues = {"RECENT", "VIEW", "LIKE"})
        @Pattern(regexp = "^(RECENT|VIEW|LIKE)$", message = "정렬 타입은 RECENT, VIEW, LIKE 중 하나여야 합니다.")
        String sortBy
) {
}
