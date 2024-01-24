package com.example.base.food.domain;

import com.example.base.commentable.domain.Commentable;
import com.example.base.common.service.port.ClockHolder;
import com.example.base.food.domain.dto.FoodCreate;
import com.example.base.reportable.domain.ActiveStatus;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class Food extends Commentable {
    private int daysBeforeTest;
    private String mainIngredient;

    public static Food from(FoodCreate foodCreate, ClockHolder clockHolder) {
        Food food = new Food();
        food.setName(foodCreate.name());
        food.setContent(foodCreate.content());
        food.setUserInformation(foodCreate.userInformation());
        food.setPassword(foodCreate.password());
        food.setStatus(ActiveStatus.ACTIVE);
        food.setDaysBeforeTest(foodCreate.daysBeforeTest());
        food.setMainIngredient(foodCreate.mainIngredient());
        food.setCreatedAt(clockHolder.millis());
        food.setLikes(0);
        food.setDislikes(0);
        food.setViews(0);
        return food;
    }
}
