package com.example.base.review.domain.dto;


import com.example.base.common.service.port.PasswordHolder;
import com.example.base.review.controller.request.ReviewCreateRequest;
import lombok.Builder;

@Builder
public record ReviewCreate(
    String name,
    String userInformation,
    String password,
    String content,
    int age,
    char gender,
    boolean sleepFlag,
    String result
) {
    public static ReviewCreate from(ReviewCreateRequest reviewCreateRequest, String userInformation, PasswordHolder passwordHolder) {
        String result = String.join("&", reviewCreateRequest.result());
        return ReviewCreate.builder()
            .name(reviewCreateRequest.name())
            .userInformation(userInformation)
            .password(passwordHolder.encrypt(reviewCreateRequest.password()))
            .content(reviewCreateRequest.content())
            .age(reviewCreateRequest.age())
            .gender(reviewCreateRequest.gender())
            .sleepFlag(reviewCreateRequest.sleepFlag())
            .result(result)
            .build();
    }
}
