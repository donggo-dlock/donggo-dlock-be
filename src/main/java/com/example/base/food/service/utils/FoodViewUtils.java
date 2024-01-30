package com.example.base.food.service.utils;

import com.example.base.commentable.domain.ViewUtils;
import com.example.base.food.domain.Food;
import com.example.base.food.service.port.FoodRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class FoodViewUtils implements ViewUtils {
    private final FoodRepository foodRepository;


    @Override
    public void reflectToDatabase(Long key, Integer count) {
        Food food = foodRepository.get(key);
        food.updateViews(count);
        foodRepository.save(food);
    }
}
