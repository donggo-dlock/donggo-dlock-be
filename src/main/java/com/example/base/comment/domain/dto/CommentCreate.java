package com.example.base.comment.domain.dto;

import com.example.base.comment.controller.request.CommentCreateRequest;
import com.example.base.comment.domain.ReferenceType;
import com.example.base.common.service.port.PasswordHolder;
import lombok.Builder;

@Builder
public record CommentCreate(
        String name,
        String password,
        String content,
        ReferenceType referenceType,
        String userInformation
) {
    public static CommentCreate from(CommentCreateRequest request, String userInformation, PasswordHolder passwordHolder) {
        return CommentCreate.builder()
                .name(request.name())
                .password(passwordHolder.encrypt(request.password()))
                .content(request.content())
                .referenceType(request.referenceType())
                .userInformation(userInformation)
                .build();
    }
}
