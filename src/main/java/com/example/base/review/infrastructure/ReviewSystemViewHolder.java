package com.example.base.review.infrastructure;

import com.example.base.food.service.utils.FoodViewUtils;
import com.example.base.review.service.port.ReviewViewHolder;
import com.github.benmanes.caffeine.cache.Cache;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ReviewSystemViewHolder implements ReviewViewHolder {
    @Qualifier("reviewViewCountCache")
    private final Cache<Long, Integer> reviewViewCache;
    private final FoodViewUtils viewUtils;

    @Override
    public void increase(Long id) {
        reviewViewCache.asMap().compute(id, (k, count) -> (count == null) ? 1 : count + 1);
    }

    @Scheduled(fixedRate = 4*60*1000)
    public void clearCache() {
        reviewViewCache.asMap().forEach(
                (key, count) -> {
                    viewUtils.reflectToDatabase(key, count);
                    reviewViewCache.invalidate(key);
                }
        );
    }
}
