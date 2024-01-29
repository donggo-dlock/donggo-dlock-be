package com.example.base.mock;

import com.example.base.commentable.domain.dto.RecommendationCreate;
import com.example.base.commentable.domain.exception.DuplicateRecommendationException;
import com.example.base.commentable.service.port.RecommendationHolder;

import java.util.HashMap;

public class TestRecommendationHolder implements RecommendationHolder {

    HashMap<String, Boolean> recommendationCache = new HashMap<>();

    @Override
    public void hasAlreadyDoneToday(String key) {
        if (recommendationCache.containsKey(key)) {
            throw new DuplicateRecommendationException();
        }
        recommendationCache.put(key, true);
    }

    @Override
    public String generateKey(String userInformation, RecommendationCreate recommendationCreate) {
        return userInformation + "&" + (recommendationCreate.RecommendationFlag() ? "Y" : "N") + "&" + recommendationCreate.Category() + "&" + recommendationCreate.id();
    }
}
