package com.example.base.comment.domain;

import com.example.base.comment.controller.request.CommentCreateRequest;
import com.example.base.commentable.domain.Commentable;
import com.example.base.common.service.port.ClockHolder;
import com.example.base.common.service.port.PasswordHolder;
import com.example.base.reportable.domain.ActiveStatus;
import com.example.base.reportable.domain.Reportable;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class Comment extends Reportable {
    private String name;
    private ReferenceType referenceType;
    private Commentable reference;

    public static Comment from(Commentable reference, CommentCreateRequest request, ClockHolder clockHolder, PasswordHolder passwordHolder, String userInformation) {
        Comment newComment = new Comment();
        newComment.setName(request.name());
        newComment.setUserInformation(userInformation);
        newComment.setPassword(passwordHolder.encrypt(request.password()));
        newComment.setContent(request.content());
        newComment.setReferenceType(request.referenceType());
        newComment.setReference(reference);
        newComment.setCreatedAt(clockHolder.millis());
        newComment.setStatus(ActiveStatus.ACTIVE);
        return newComment;
    }
}
