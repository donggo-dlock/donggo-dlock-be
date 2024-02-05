package com.example.base.food.domain.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Pattern;
import lombok.Builder;

@Builder
public record FoodSearch(
        @Schema(description = "검색어, 제목 기준", example = "피자")
        String keyword,

        @Schema(description = "검사 n일전, null 값 가능", example = "3")
        @Pattern(regexp = "(^$|[1-3])", message = "1-3의 숫자만 입력 가능합니다.")
        String daysBeforeTest,

        @Schema(description = "정렬 타입", example = "RECENT", defaultValue = "RECENT", allowableValues = {"RECENT", "VIEW", "LIKE"})
        @Pattern(regexp = "^(RECENT|VIEW|LIKE)$", message = "정렬 타입은 RECENT, VIEW, LIKE 중 하나여야 합니다.")
        String sortBy
) {
}
