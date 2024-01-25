package com.example.base.food.infrastructure.utils;

import com.example.base.food.domain.Food;
import com.example.base.food.service.port.FoodRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class FoodViewUtils {
    private final FoodRepository foodRepository;

    @Transactional
    public void reflectToDatabase(Long key, Integer count) {
        Food food = foodRepository.get(key);
        food.updateViews(count);
        foodRepository.save(food);
    }
}
