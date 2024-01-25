package com.example.base.food.infrastructure;

import com.example.base.food.infrastructure.utils.FoodViewUtils;
import com.example.base.food.service.port.FoodViewHolder;
import com.github.benmanes.caffeine.cache.Cache;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class FoodSystemViewHolder implements FoodViewHolder {
    @Qualifier("foodViewCountCache")
    private final Cache<Long, Integer> foodViewCountCache;
    private final FoodViewUtils viewUtils;

    @Override
    public void increase(Long id) {
        foodViewCountCache.asMap().compute(id, (k, count) -> (count == null) ? 1 : count + 1);
    }

    @Scheduled(fixedRate = 4*60*1000)
    public void clearCache() {
        foodViewCountCache.asMap().forEach(
                (key, count) -> {
                    viewUtils.reflectToDatabase(key, count);
                    foodViewCountCache.invalidate(key);
                }
        );
    }
}
