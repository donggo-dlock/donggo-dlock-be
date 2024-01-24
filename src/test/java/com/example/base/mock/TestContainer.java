package com.example.base.mock;

import com.example.base.common.service.port.ClockHolder;
import com.example.base.food.controller.port.FoodService;
import com.example.base.food.service.FoodServiceImpl;
import com.example.base.food.service.port.FoodRepository;
import lombok.Builder;

//Spring IOC 컨테이너 기능 구현
public class TestContainer {
    public final FoodRepository foodRepository;
    public final FoodService foodService;

    @Builder
    private TestContainer(ClockHolder clockHolder) {
        this.foodRepository = new FakeFoodRepository();
        this.foodService = FoodServiceImpl.builder()
            .foodRepository(foodRepository)
            .clockHolder(clockHolder)
            .build();
    }
}
