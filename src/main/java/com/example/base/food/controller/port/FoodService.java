package com.example.base.food.controller.port;

import com.example.base.food.controller.request.FoodCreateRequest;
import com.example.base.food.controller.response.FoodInfoResponse;
import com.example.base.food.controller.response.FoodResponse;
import com.example.base.commentable.domain.dto.CommentableDelete;
import com.example.base.food.domain.dto.FoodSearch;
import com.example.base.web.dto.PageCreate;
import com.example.base.web.dto.PageResponse;

public interface FoodService {
    public void create(FoodCreateRequest foodCreateRequest, String ipAddress);
    public PageResponse<FoodResponse> getPagination(PageCreate pageCreate, FoodSearch foodSearch);
    public FoodInfoResponse get(String name);
    public FoodInfoResponse get(Long id);
    public void delete(CommentableDelete commentableDelete, Long id);
    public void updateRecommendations(Long id, Boolean recommendationFlag);

}
