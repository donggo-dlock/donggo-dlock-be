package com.example.base.food.controller.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import org.hibernate.validator.constraints.Length;

@Builder
@Schema(description = "음식 생성 요청")
public record FoodCreateRequest(
    @Schema(description = "음식 이름", example = "피자")
    @Length(min = 1, max = 50, message = "음식 이름은 1자 이상 50자 이하로 입력해주세요.")
    String name,

    @Schema(description = "비밀번호", example = "1234")
    @Length(min = 4, max = 4, message = "비밀번호는 4자로 입력해주세요.")
    String password,

    @Schema(description = "음식 설명", example = "맛있는 피자")
    @Length(min = 1, max = 255, message = "음식 설명은 1자 이상 255자 이하로 입력해주세요.")
    String content,

    @Schema(description = "테스트까지 남은 일수", example = "3")
    @Min(value = 1, message = "테스트까지 남은 일수는 1일 이상으로 입력해주세요.")
    @Max(value = 3, message = "테스트까지 남은 일수는 3일 이하로 입력해주세요.")
    int daysBeforeTest,

    @Schema(description = "주재료", example = "#밀가루")
    @NotBlank(message = "주재료는 필수로 입력해주세요.")
    String mainIngredient
) {
}
