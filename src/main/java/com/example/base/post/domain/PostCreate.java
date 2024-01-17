package com.example.base.post.domain;

import lombok.Builder;

@Builder
public record PostCreate(
        long writerId,
        String content
){
}
