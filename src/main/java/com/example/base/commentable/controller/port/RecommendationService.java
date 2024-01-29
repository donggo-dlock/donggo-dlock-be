package com.example.base.commentable.controller.port;

import com.example.base.commentable.domain.dto.RecommendationCreate;

public interface RecommendationService {
    public void create(final String userInformation, final RecommendationCreate recommendationCreate);
}
