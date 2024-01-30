package com.example.base.review.controller.port;

import com.example.base.reportable.domain.dto.ReportableDelete;
import com.example.base.review.controller.request.ReviewCreateRequest;
import com.example.base.review.controller.response.ReviewInfoResponse;
import com.example.base.review.controller.response.ReviewResponse;
import com.example.base.review.domain.Review;
import com.example.base.review.domain.dto.ReviewSearch;
import com.example.base.web.dto.PageCreate;
import com.example.base.web.dto.PageResponse;

public interface ReviewService {
    public void create(ReviewCreateRequest reviewCreateRequest, String ipAddress);
    public void updateRecommendations(Long id, Boolean recommendationFlag);

    ReviewInfoResponse get(Long id);
    Review getByName(String name);

    PageResponse<ReviewResponse> getPagination(PageCreate pageCreate, ReviewSearch reviewSearch);

    void delete(ReportableDelete reportableDelete, Long id);
}
