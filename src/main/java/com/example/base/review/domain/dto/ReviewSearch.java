package com.example.base.review.domain.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.annotation.Nullable;
import jakarta.validation.constraints.Pattern;
import lombok.Builder;

@Builder
public record ReviewSearch(
        @Schema(description = "검색어, 제목 기준", example = "수면")
        String keyword,

        @Schema(description = "성별, null 값 가능", example = "M", nullable = true, allowableValues = {"M", "F"})
        @Nullable
        char gender,

        @Schema(description = "나이, 구분자는 #입니다.", example = "20")
        @Nullable
        String age,

        @Schema(description = "수면 여부", example = "true")
        @Nullable
        Boolean sleepFlag,

        @Schema(description = "검사 결과, 구분자는 #입니다", example = "용종#종양")
        @Nullable
        String result,


        @Schema(description = "정렬 타입", example = "RECENT", defaultValue = "RECENT", allowableValues = {"RECENT", "VIEW", "LIKE"})
        @Pattern(regexp = "^(RECENT|VIEW|LIKE)$", message = "정렬 타입은 RECENT, VIEW, LIKE 중 하나여야 합니다.")
        String sortBy
) {
}
