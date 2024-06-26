package com.example.base.food.controller;

import com.example.base.food.controller.port.FoodService;
import com.example.base.food.controller.request.FoodCreateRequest;
import com.example.base.food.controller.response.FoodInfoResponse;
import com.example.base.food.controller.response.FoodResponse;
import com.example.base.food.domain.dto.FoodSearch;
import com.example.base.reportable.domain.dto.ReportableDelete;
import com.example.base.web.annotation.IpAddress;
import com.example.base.web.dto.PageCreate;
import com.example.base.web.dto.PageResponse;
import io.swagger.v3.oas.annotations.Parameter;
import jakarta.validation.Valid;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Builder
@RestController
@RequestMapping("/foods")
@RequiredArgsConstructor
@Validated
public class FoodController {

    private final FoodService foodService;

    @PostMapping
    public void create(@Valid @RequestBody FoodCreateRequest foodCreateRequest, @Parameter(hidden = true) @IpAddress String ipAddress) {
        foodService.create(foodCreateRequest, ipAddress);
    }

    @GetMapping("/{id}")
    public FoodInfoResponse getInfo(@PathVariable Long id) {
        return foodService.getFoodInfoResponse(id);
    }

    @GetMapping("/list")
    public PageResponse<FoodResponse> getPagination(@Valid @ParameterObject FoodSearch foodSearch, @ParameterObject PageCreate pageCreate) {
        return foodService.getPagination(pageCreate, foodSearch);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id, @Valid @RequestBody ReportableDelete reportableDelete) {
        foodService.delete(reportableDelete, id);
    }
}