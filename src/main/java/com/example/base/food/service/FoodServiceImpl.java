package com.example.base.food.service;


import com.example.base.common.service.port.ClockHolder;
import com.example.base.common.service.port.PasswordHolder;
import com.example.base.food.controller.port.FoodService;
import com.example.base.food.controller.request.FoodCreateRequest;
import com.example.base.food.controller.response.FoodInfoResponse;
import com.example.base.food.controller.response.FoodResponse;
import com.example.base.food.domain.Food;
import com.example.base.food.domain.dto.FoodCreate;
import com.example.base.food.domain.dto.FoodDelete;
import com.example.base.food.domain.dto.FoodSearch;
import com.example.base.food.service.port.FoodRepository;
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
public class FoodServiceImpl implements FoodService {

    private final FoodRepository foodRepository;
    private final ClockHolder clockHolder;
    private final PasswordHolder passwordHolder;

    @Override
    public void create(FoodCreateRequest foodCreateRequest, String userInformation) {
        foodRepository.save(Food.from(FoodCreate.from(foodCreateRequest, userInformation), clockHolder, passwordHolder));
    }

    @Override
    public PageResponse<FoodResponse> getPagination(PageCreate pageCreate, FoodSearch foodSearch) {
        PageResponse<Food> foodPageResponse = foodRepository.getPage(pageCreate, foodSearch);
        List<FoodResponse> foodResponseList =foodPageResponse.getContent().stream()
                .map(FoodResponse::from)
                .toList();
        return PageResponse.of(foodResponseList,pageCreate,foodPageResponse.getTotal());
    }

    @Override
    public FoodInfoResponse get(String name) {
        return FoodInfoResponse.from(foodRepository.get(name), clockHolder);
    }

    @Override
    public FoodInfoResponse get(Long id) {
        return FoodInfoResponse.from(foodRepository.get(id), clockHolder);
    }

    @Override
    public void delete(FoodDelete foodDelete, Long id) {
        Food food = foodRepository.get(id);
        passwordHolder.match(foodDelete.password(), food.getPassword());
        foodRepository.delete(food);
    }
}