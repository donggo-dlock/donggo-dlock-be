package com.example.base.food.controller.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

@Builder
@Schema(description = "음식 생성 요청")
public record FoodCreateRequest(
    String name,
    String password,
    String content,
    int daysBeforeTest,
    String mainIngredient
) {
}
