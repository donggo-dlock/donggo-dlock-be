package com.example.base.food.controller.response;

import com.example.base.food.domain.Food;
import lombok.Builder;

@Builder
public record FoodResponse(
        Long id,
        String name,
        int likes,
        int dislikes
) {
    public static FoodResponse from(Food food) {
        return FoodResponse.builder()
                .id(food.getId())
                .name(food.getName())
                .likes(food.getLikes())
                .dislikes(food.getDislikes())
                .build();
    }
}
