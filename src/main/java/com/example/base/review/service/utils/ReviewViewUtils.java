package com.example.base.review.service.utils;

import com.example.base.commentable.domain.ViewUtils;
import com.example.base.review.domain.Review;
import com.example.base.review.service.port.ReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ReviewViewUtils implements ViewUtils {
    private final ReviewRepository reviewRepository;

    @Override
    public void reflectToDatabase(Long key, Integer count) {
        Review review = reviewRepository.get(key);
        review.updateViews(count);
        reviewRepository.save(review);
    }
}
