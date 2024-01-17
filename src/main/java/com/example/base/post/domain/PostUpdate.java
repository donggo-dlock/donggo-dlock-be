package com.example.base.post.domain;

import lombok.Builder;

@Builder
public record PostUpdate(
        String content
) {
}
