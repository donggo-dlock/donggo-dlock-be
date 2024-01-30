package com.example.base.review.domain;

import com.example.base.commentable.domain.Commentable;
import com.example.base.common.service.port.ClockHolder;
import com.example.base.reportable.domain.ActiveStatus;
import com.example.base.review.domain.dto.ReviewCreate;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Review extends Commentable {

    private char gender;
    private int age;
    private Boolean sleepFlag;
    private String result;

    public static Review from(ReviewCreate reviewCreate, ClockHolder clockHolder){
        Review review = new Review();
        review.setName(reviewCreate.name());
        review.setContent(reviewCreate.content());
        review.setUserInformation(reviewCreate.userInformation());
        review.setPassword(reviewCreate.password());
        review.setStatus(ActiveStatus.ACTIVE);
        review.setGender(reviewCreate.gender());
        review.setAge(reviewCreate.age());
        review.setSleepFlag(reviewCreate.sleepFlag());
        review.setResult(reviewCreate.result());
        review.setCreatedAt(clockHolder.millis());
        review.setLikes(0);
        review.setDislikes(0);
        review.setViews(0);
        return review;
    }
}
