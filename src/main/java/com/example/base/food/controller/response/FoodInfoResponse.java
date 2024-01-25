package com.example.base.food.controller.response;


import com.example.base.common.service.port.ClockHolder;
import com.example.base.food.domain.Food;
import com.example.base.reportable.domain.ActiveStatus;
import lombok.Builder;

@Builder
public record FoodInfoResponse(
    Long id,
    String name,
    String content,
    int daysBeforeTest,
    String mainIngredient,
    int views,
    int likes,
    int dislikes,
    String createdAt,
    ActiveStatus status
) {
    public static FoodInfoResponse from(Food food, ClockHolder clockHolder) {
        return FoodInfoResponse.builder()
                .id(food.getId())
                .name(food.getName())
                .content(food.getContent())
                .daysBeforeTest(food.getDaysBeforeTest())
                .mainIngredient(food.getMainIngredient())
                .views(food.getViews())
                .likes(food.getLikes())
                .dislikes(food.getDislikes())
                .createdAt(clockHolder.dateTime(food.getCreatedAt()))
                .status(food.getStatus())
                .build();
    }
}
