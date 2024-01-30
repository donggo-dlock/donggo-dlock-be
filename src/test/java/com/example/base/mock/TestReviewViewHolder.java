package com.example.base.mock;

import com.example.base.review.domain.Review;
import com.example.base.review.service.port.ReviewRepository;
import com.example.base.review.service.port.ReviewViewHolder;

public class TestReviewViewHolder implements ReviewViewHolder {
    private final ReviewRepository reviewRepository;

    public TestReviewViewHolder(ReviewRepository reviewRepository) {
        this.reviewRepository = reviewRepository;
    }

    @Override
    public void increase(Long id) {
        Review review = reviewRepository.get(id);
        review.updateViews(review.getViews() + 1);
        reviewRepository.save(review);
    }
}
