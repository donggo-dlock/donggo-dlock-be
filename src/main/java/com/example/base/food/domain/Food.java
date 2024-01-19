package com.example.base.food.domain;

import lombok.Builder;

@Builder
public record Food(
        Long id,
        String name,
        String userInformation,
        String password,
        int daysBeforeTest,
        String mainIngredient,
        String content,
        int views,
        int likes,
        int dislikes,
        FoodStatus status,
        Long createdAt
) {
}
