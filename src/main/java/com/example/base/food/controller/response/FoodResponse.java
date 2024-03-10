package com.example.base.food.controller.response;

import com.example.base.common.service.port.ClockHolder;
import com.example.base.food.domain.Food;
import lombok.Builder;

@Builder
public record FoodResponse(
        Long id,
        String name,
        int likes,
        int views,
        String createdAt
) {
    public static FoodResponse from(Food food, ClockHolder clockHolder) {
        return FoodResponse.builder()
                .id(food.getId())
                .name(food.getName())
                .likes(food.getLikes())
                .views(food.getViews())
                .createdAt(clockHolder.dateTime(food.getCreatedAt()))
                .build();
    }
}
