package com.example.base.post.controller.response;

import com.example.base.post.domain.Post;
import com.example.base.user.controller.response.UserResponse;
import lombok.Builder;

@Builder
public record PostResponse(
    Long id,
    String content,
    Long createdAt,
    Long modifiedAt,
    UserResponse writer
){
    public static PostResponse from(Post post) {
        return PostResponse.builder()
            .id(post.id())
            .content(post.content())
            .createdAt(post.createdAt())
            .modifiedAt(post.modifiedAt())
            .writer(UserResponse.from(post.writer()))
            .build();
    }
}
