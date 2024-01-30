package com.example.base.mock;

import com.example.base.food.domain.Food;
import com.example.base.food.service.port.FoodRepository;
import com.example.base.food.service.port.FoodViewHolder;

public class TestFoodViewHolder implements FoodViewHolder {
    private final FoodRepository foodRepository;

    public TestFoodViewHolder(FoodRepository foodRepository) {
        this.foodRepository = foodRepository;
    }

    @Override
    public void increase(Long id) {
        Food food = foodRepository.get(id);
        food.updateViews(food.getViews() + 1);
        foodRepository.save(food);
    }
}
