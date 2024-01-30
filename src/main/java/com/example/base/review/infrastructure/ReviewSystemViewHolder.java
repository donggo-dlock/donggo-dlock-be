package com.example.base.review.infrastructure;

import com.example.base.review.service.port.ReviewViewHolder;
import com.example.base.review.service.utils.ReviewViewUtils;
import com.github.benmanes.caffeine.cache.Cache;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ReviewSystemViewHolder implements ReviewViewHolder {
    @Qualifier("reviewViewCountCache")
    private final Cache<Long, Integer> reviewViewCountCache;
    private final ReviewViewUtils viewUtils;

    @Override
    public void increase(Long id) {
        reviewViewCountCache.asMap().compute(id, (k, count) -> (count == null) ? 1 : count + 1);
    }

    @Scheduled(fixedRate = 4*60*1000)
    public void clearCache() {
        reviewViewCountCache.asMap().forEach(
                (key, count) -> {
                    viewUtils.reflectToDatabase(key, count);
                    reviewViewCountCache.invalidate(key);
                }
        );
    }
}
