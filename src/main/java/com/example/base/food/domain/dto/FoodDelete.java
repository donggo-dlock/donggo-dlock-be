package com.example.base.food.domain.dto;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

@Builder
@Schema(name = "FoodDelete", description = "Food delete request")
public record FoodDelete(
    String password
) {
}
