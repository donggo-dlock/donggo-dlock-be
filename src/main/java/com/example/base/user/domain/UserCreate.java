package com.example.base.user.domain;

import lombok.Builder;

@Builder
public record UserCreate(
    String email,
    String nickname,
    String address
) {
}
