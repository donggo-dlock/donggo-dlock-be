package com.example.base.comment.domain;

import com.example.base.commentable.domain.Commentable;
import com.example.base.reportable.domain.Reportable;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class Comment extends Reportable {
    private ReferenceType referenceType;
    private Commentable reference;
}
