package com.example.base.post.domain;

import com.example.base.common.service.port.ClockHolder;
import com.example.base.user.domain.User;
import lombok.Builder;

@Builder
public record Post(
        Long id,
        String content,
        Long createdAt,
        Long modifiedAt,
        User writer
) {
    public static Post from(PostCreate postCreate, User writer, ClockHolder clockHolder) {
        return Post.builder()
            .content(postCreate.content())
            .createdAt(clockHolder.millis())
            .writer(writer)
            .build();
    }

    public Post update(PostUpdate postUpdate, ClockHolder clockHolder) {
        return Post.builder()
            .id(this.id)
            .content(postUpdate.content())
            .createdAt(this.createdAt)
            .modifiedAt(clockHolder.millis())
            .writer(this.writer)
            .build();
    }
}
