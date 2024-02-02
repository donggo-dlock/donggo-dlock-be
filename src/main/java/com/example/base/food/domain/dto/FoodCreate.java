package com.example.base.food.domain.dto;


import com.example.base.food.controller.request.FoodCreateRequest;
import lombok.Builder;

@Builder
public record FoodCreate(
    String name,
    String userInformation,
    String password,
    String content,
    int daysBeforeTest,
    String mainIngredient
) {
    public static FoodCreate from(FoodCreateRequest foodCreateRequest, String userInformation) {
        String mainIngredient = String.join("&", foodCreateRequest.mainIngredient());
        return FoodCreate.builder()
            .name(foodCreateRequest.name())
            .userInformation(userInformation)
            .password(foodCreateRequest.password())
            .content(foodCreateRequest.content())
            .daysBeforeTest(foodCreateRequest.daysBeforeTest())
            .mainIngredient(mainIngredient)
            .build();
    }
}
