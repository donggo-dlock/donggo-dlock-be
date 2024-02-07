package com.example.base.review.domain;

import com.example.base.commentable.domain.Commentable;
import com.example.base.common.service.port.ClockHolder;
import com.example.base.common.service.port.PasswordHolder;
import com.example.base.reportable.domain.ActiveStatus;
import com.example.base.review.controller.request.ReviewCreateRequest;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Review extends Commentable {

    private char gender;
    private int age;
    private Boolean sleepFlag;
    private String result;

    public static Review from(ReviewCreateRequest reviewCreateRequest, String ipAddress, ClockHolder clockHolder, PasswordHolder passwordHolder){
        Review review = new Review();
        String result = String.join("&", reviewCreateRequest.result());
        review.setName(reviewCreateRequest.name());
        review.setContent(reviewCreateRequest.content());
        review.setUserInformation(ipAddress);
        review.setPassword(passwordHolder.encrypt(reviewCreateRequest.password()));
        review.setStatus(ActiveStatus.ACTIVE);
        review.setGender(reviewCreateRequest.gender());
        review.setAge(reviewCreateRequest.age());
        review.setSleepFlag(reviewCreateRequest.sleepFlag());
        review.setResult(result);
        review.setCreatedAt(clockHolder.millis());
        review.setLikes(0);
        review.setDislikes(0);
        review.setViews(0);
        return review;
    }
}
