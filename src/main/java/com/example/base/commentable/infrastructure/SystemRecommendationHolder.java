package com.example.base.commentable.infrastructure;

import com.example.base.commentable.domain.dto.RecommendationCreate;
import com.example.base.commentable.domain.exception.DuplicateRecommendationException;
import com.example.base.commentable.service.port.RecommendationHolder;
import com.github.benmanes.caffeine.cache.Cache;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SystemRecommendationHolder implements RecommendationHolder {
    private final Cache<String, Boolean> recommendationCache;

    @Override
    public void hasAlreadyDoneToday(String key) {
        recommendationCache.asMap().compute(key, (k, v) -> {
            if (v != null) {
                throw new DuplicateRecommendationException();
            }
            return true;
        });
    }

    @Override
    public String generateKey(String userInformation, RecommendationCreate recommendationCreate) {
        String separator = "&";
        return userInformation +
                separator +
                (recommendationCreate.RecommendationFlag() ? "Y" : "N") +
                separator +
                recommendationCreate.Category() +
                separator +
                recommendationCreate.id();
    }
}
