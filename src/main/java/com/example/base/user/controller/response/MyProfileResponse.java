package com.example.base.user.controller.response;


import com.example.base.user.domain.User;
import com.example.base.user.domain.UserStatus;
import lombok.Builder;

@Builder
public record MyProfileResponse (
        Long id,
        String email,
        String nickname,
        String address,
        UserStatus status,
        Long lastLoginAt
){
    public static MyProfileResponse from(User user) {
        return MyProfileResponse.builder()
                .id(user.id())
                .email(user.email())
                .nickname(user.nickname())
                .address(user.address())
                .status(user.status())
                .lastLoginAt(user.lastLoginAt())
                .build();
    }
}
