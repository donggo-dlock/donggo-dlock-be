package com.example.base.commentable.domain;

import com.example.base.reportable.domain.Reportable;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public abstract class Commentable extends Reportable {
    private String name;
    private int views;
    private int likes;
    private int dislikes;
}
