package com.example.base.food.domain;

import com.example.base.commentable.domain.Commentable;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class Food extends Commentable {
    private int daysBeforeTest;
    private String mainIngredient;
}
