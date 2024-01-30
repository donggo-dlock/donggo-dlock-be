package com.example.base.food.controller.port;

import com.example.base.food.controller.request.FoodCreateRequest;
import com.example.base.food.controller.response.FoodInfoResponse;
import com.example.base.food.controller.response.FoodResponse;
import com.example.base.food.domain.Food;
import com.example.base.reportable.domain.dto.ReportableDelete;
import com.example.base.food.domain.dto.FoodSearch;
import com.example.base.web.dto.PageCreate;
import com.example.base.web.dto.PageResponse;

public interface FoodService {
    public void create(FoodCreateRequest foodCreateRequest, String ipAddress);
    public PageResponse<FoodResponse> getPagination(PageCreate pageCreate, FoodSearch foodSearch);
    public Food get(String name);
    public Food get(Long id);
    public FoodInfoResponse getFoodInfoResponse(Long id);
    public void delete(ReportableDelete reportableDelete, Long id);
    public void updateRecommendations(Long id, Boolean recommendationFlag);

}
