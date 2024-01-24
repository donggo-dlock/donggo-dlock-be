package com.example.base.food.domain.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Pattern;
import lombok.Builder;

@Builder
public record FoodSearch(
        @Schema(description = "검색어, 제목 기준", example = "피자")
        String keyword,

        @Max(value = 3, message = "검사 일수 최대값은 3일입니다.")
        @Min(value = 1, message = "검사 일수 최소값은 1일입니다.")
        int daysBeforeTest,

        @Pattern(regexp = "^(RECENT|VIEW|LIKE)$", message = "정렬 타입은 RECENT, VIEW, LIKE 중 하나여야 합니다.")
        String sortBy
) {
}
