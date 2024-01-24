package com.example.base.food.infrastructure;

import com.example.base.food.domain.Food;
import com.example.base.reportable.domain.ActiveStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "foods")
public class FoodEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", columnDefinition = "varchar(50)")
    private String name;

    @Column(name = "userInformation", columnDefinition = "varchar(50)")
    private String userInformation;

    @Column(name = "password", columnDefinition = "varchar(500)")
    private String password;

    @Column(name = "content", columnDefinition = "varchar(255)")
    private String content;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private ActiveStatus status;

    @Column(name = "daysBeforeTest")
    private int daysBeforeTest;

    @Column(name = "mainIngredient", columnDefinition = "varchar(50)")
    private String mainIngredient;

    @Column(name = "views")
    private int views;

    @Column(name = "likes")
    private int likes;

    @Column(name = "dislikes")
    private int dislikes;

    @Column(name = "created_at")
    private Long createdAt;

    public static FoodEntity from(Food food) {
        FoodEntity foodEntity = new FoodEntity();
        foodEntity.setId(food.getId());
        foodEntity.setName(food.getName());
        foodEntity.setUserInformation(food.getUserInformation());
        foodEntity.setPassword(food.getPassword());
        foodEntity.setContent(food.getContent());
        foodEntity.setDaysBeforeTest(food.getDaysBeforeTest());
        foodEntity.setMainIngredient(food.getMainIngredient());
        foodEntity.setViews(food.getViews());
        foodEntity.setLikes(food.getLikes());
        foodEntity.setDislikes(food.getDislikes());
        foodEntity.setCreatedAt(food.getCreatedAt());
        foodEntity.setStatus(food.getStatus());
        return foodEntity;
    }

    public Food toModel() {
        Food food = new Food();
        food.setId(id);
        food.setName(name);
        food.setUserInformation(userInformation);
        food.setPassword(password);
        food.setContent(content);
        food.setDaysBeforeTest(daysBeforeTest);
        food.setMainIngredient(mainIngredient);
        food.setViews(views);
        food.setLikes(likes);
        food.setDislikes(dislikes);
        food.setCreatedAt(createdAt);
        food.setStatus(status);
        return food;
    }

}