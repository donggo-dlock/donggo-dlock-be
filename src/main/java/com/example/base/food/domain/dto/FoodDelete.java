package com.example.base.food.domain.dto;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import org.hibernate.validator.constraints.Length;

@Builder
@Schema(name = "FoodDelete", description = "Food delete request")
public record FoodDelete(

    @Schema(description = "Password", example = "1234")
    @Length(min = 4, max = 4, message = "Password must be 4 digits")
    String password
) {
}
