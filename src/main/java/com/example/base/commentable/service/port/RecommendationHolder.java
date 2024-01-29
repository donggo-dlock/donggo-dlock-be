package com.example.base.commentable.service.port;

import com.example.base.commentable.domain.dto.RecommendationCreate;

public interface RecommendationHolder {
    public void hasAlreadyDoneToday(final String key);

    public String generateKey(final String userInformation, final RecommendationCreate recommendationCreate);
}
