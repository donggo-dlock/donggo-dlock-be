package com.example.base.commentable.service;

import com.example.base.commentable.controller.port.RecommendationService;
import com.example.base.commentable.domain.dto.RecommendationCreate;
import com.example.base.commentable.service.port.RecommendationHolder;
import com.example.base.food.controller.port.FoodService;
import com.example.base.review.controller.port.ReviewService;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Builder
@Service
@RequiredArgsConstructor
@Transactional(propagation = Propagation.REQUIRES_NEW)
public class RecommendationServiceImpl implements RecommendationService{
    private final RecommendationHolder recommendationHolder;
    private final FoodService foodService;
    private final ReviewService reviewService;

    @Override
    public void create(String userInformation, RecommendationCreate recommendationCreate) {
        String key = recommendationHolder.generateKey(userInformation, recommendationCreate);
        recommendationHolder.hasAlreadyDoneToday(key);
        processRecommendation(recommendationCreate);
    }

    private void processRecommendation(RecommendationCreate recommendationCreate) {
        switch (recommendationCreate.Category()) {
            case "FOOD":
                foodService.updateRecommendations(recommendationCreate.id(), recommendationCreate.RecommendationFlag());
                break;
            case "REVIEW":
                reviewService.updateRecommendations(recommendationCreate.id(), recommendationCreate.RecommendationFlag());
                break;
            default:
                throw new IllegalArgumentException("존재하지 않는 카테고리입니다.");
        }
    }
}
