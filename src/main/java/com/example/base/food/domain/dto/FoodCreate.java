package com.example.base.food.domain.dto;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

@Builder
@Schema(name = "FoodCreate", description = "Food create request")
public record FoodCreate(
    String name,
    String userInformation,
    String password,
    String content,
    int daysBeforeTest,
    String mainIngredient
) {
}
