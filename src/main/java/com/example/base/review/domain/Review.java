package com.example.base.review.domain;

import com.example.base.commentable.domain.Commentable;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Review extends Commentable {

    private char gender;
    private int age;
    private String title;
    private Boolean sleepFlag;
    private String result;

}
