package com.example.base.comment.controller.response;

import com.example.base.comment.domain.Comment;
import com.example.base.common.service.port.ClockHolder;
import lombok.Builder;

@Builder
public record CommentResponse(
        Long id,
        String name,
        String content,
        String createdAt
) {
    public static CommentResponse from(Comment comment, ClockHolder clockHolder) {
        return CommentResponse.builder()
                .id(comment.getId())
                .name(comment.getName())
                .content(comment.getContent())
                .createdAt(clockHolder.dateTime(comment.getCreatedAt()))
                .build();
    }
}
