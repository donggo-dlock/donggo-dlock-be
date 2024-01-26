package com.example.base.review.service;


import com.example.base.common.service.port.ClockHolder;
import com.example.base.review.controller.port.ReviewService;
import com.example.base.review.domain.Review;
import com.example.base.review.service.port.ReviewRepository;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@Builder
@RequiredArgsConstructor
public class ReviewServiceImpl implements ReviewService {
    private final ReviewRepository reviewRepository;
    private final ClockHolder clockHolder;
    @Override
    public void updateRecommendations(Long id, Boolean recommendationFlag) {
        Review review = reviewRepository.get(id);
        review.updateRecommendations(recommendationFlag);
        reviewRepository.save(review);
    }
}