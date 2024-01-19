package com.example.base.food.infrastructure;

import com.example.base.food.domain.Food;
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

    @Column(name = "password", columnDefinition = "varchar(4)")
    private String password;

    @Column(name = "content", columnDefinition = "varchar(255)")
    private String content;

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
        foodEntity.id = food.id();
        foodEntity.name = food.name();
        foodEntity.userInformation = food.userInformation();
        foodEntity.password = food.password();
        foodEntity.content = food.content();
        foodEntity.daysBeforeTest = food.daysBeforeTest();
        foodEntity.mainIngredient = food.mainIngredient();
        foodEntity.views = food.views();
        foodEntity.likes = food.likes();
        foodEntity.dislikes = food.dislikes();
        foodEntity.createdAt = food.createdAt();

        return foodEntity;
    }

    public Food toModel() {
        return Food.builder()
                .id(id)
                .name(name)
                .userInformation(userInformation)
                .password(password)
                .content(content)
                .daysBeforeTest(daysBeforeTest)
                .mainIngredient(mainIngredient)
                .views(views)
                .likes(likes)
                .dislikes(dislikes)
                .createdAt(createdAt)
                .build();
    }

}