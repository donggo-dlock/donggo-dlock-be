package com.example.base.review.service;


import com.example.base.reportable.domain.dto.ReportableDelete;
import com.example.base.common.service.port.ClockHolder;
import com.example.base.common.service.port.PasswordHolder;
import com.example.base.review.controller.port.ReviewService;
import com.example.base.review.controller.request.ReviewCreateRequest;
import com.example.base.review.controller.response.ReviewInfoResponse;
import com.example.base.review.controller.response.ReviewResponse;
import com.example.base.review.domain.Review;
import com.example.base.review.domain.dto.ReviewCreate;
import com.example.base.review.domain.dto.ReviewSearch;
import com.example.base.review.service.port.ReviewRepository;
import com.example.base.review.service.port.ReviewViewHolder;
import com.example.base.web.dto.PageCreate;
import com.example.base.web.dto.PageResponse;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Builder
@RequiredArgsConstructor
@Transactional(propagation = Propagation.REQUIRES_NEW)
public class ReviewServiceImpl implements ReviewService {
    private final ReviewRepository reviewRepository;
    private final PasswordHolder passwordHolder;
    private final ClockHolder clockHolder;
    private final ReviewViewHolder reviewViewHolder;

    @Override
    public void create(ReviewCreateRequest reviewCreateRequest, String ipAddress) {
        reviewRepository.save(Review.from(ReviewCreate.from(reviewCreateRequest, ipAddress, passwordHolder), clockHolder));
    }

    @Override
    public void updateRecommendations(Long id, Boolean recommendationFlag) {
        Review review = reviewRepository.get(id);
        review.updateRecommendations(recommendationFlag);
        reviewRepository.save(review);
    }

    @Override
    public ReviewInfoResponse getReviewInfoResponse(Long id) {
        Review review = reviewRepository.get(id);
        reviewViewHolder.increase(review.getId());
        return ReviewInfoResponse.from(review, clockHolder);
    }

    @Override
    public Review get(Long id) {
        return reviewRepository.get(id);
    }

    @Override
    public Review getByName(String name) {
        return reviewRepository.get(name);
    }

    @Override
    public PageResponse<ReviewResponse> getPagination(PageCreate pageCreate, ReviewSearch reviewSearch) {
        PageResponse<Review> reviewPageResponse = reviewRepository.getPage(pageCreate, reviewSearch);
        List<ReviewResponse> reviewResponseList = reviewPageResponse.getContent().stream()
                .map(review -> ReviewResponse.from(review, clockHolder))
                .toList();
        return PageResponse.of(reviewResponseList, pageCreate, reviewPageResponse.getTotal());
    }

    @Override
    public void delete(ReportableDelete reportableDelete, Long id) {
        Review review = reviewRepository.get(id);
        passwordHolder.match(reportableDelete.password(), review.getPassword());
        reviewRepository.delete(review);
    }
}