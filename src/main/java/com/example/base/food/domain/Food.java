package com.example.base.food.domain;

import com.example.base.commentable.domain.Commentable;
import com.example.base.common.service.port.ClockHolder;
import com.example.base.common.service.port.PasswordHolder;
import com.example.base.food.controller.request.FoodCreateRequest;
import com.example.base.reportable.domain.ActiveStatus;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class Food extends Commentable {
    private int daysBeforeTest;
    private String mainIngredient;

    public static Food from(FoodCreateRequest foodCreateRequest, String ipAddress, ClockHolder clockHolder, PasswordHolder passwordHolder) {
        Food food = new Food();
        String mainIngredient = String.join("&", foodCreateRequest.mainIngredient());
        food.setName(foodCreateRequest.name());
        food.setContent(foodCreateRequest.content());
        food.setUserInformation(ipAddress);
        food.setPassword(passwordHolder.encrypt(foodCreateRequest.password()));
        food.setStatus(ActiveStatus.ACTIVE);
        food.setDaysBeforeTest(foodCreateRequest.daysBeforeTest());
        food.setMainIngredient(mainIngredient);
        food.setCreatedAt(clockHolder.millis());
        food.setLikes(0);
        food.setDislikes(0);
        food.setViews(0);
        return food;
    }

}
