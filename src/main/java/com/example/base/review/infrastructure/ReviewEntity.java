package com.example.base.review.infrastructure;

import com.example.base.reportable.domain.ActiveStatus;
import com.example.base.review.domain.Review;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "reviews")
public class ReviewEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", columnDefinition = "varchar(50)")
    private String name;

    @Column(name = "userInformation", columnDefinition = "varchar(50)")
    private String userInformation;

    @Column(name = "password", columnDefinition = "varchar(255)")
    private String password;

    @Column(name = "content", columnDefinition = "varchar(255)")
    private String content;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private ActiveStatus status;

    @Column(name = "views")
    private int views;

    @Column(name = "likes")
    private int likes;

    @Column(name = "dislikes")
    private int dislikes;

    @Column(name = "created_at")
    private Long createdAt;

    @Column(name = "gender")
    private char gender;

    @Column(name = "age")
    private int age;

    @Column(name = "sleepFlag")
    private Boolean sleepFlag;

    @Column(name = "result")
    private String result;

    public static ReviewEntity from(Review review) {
        ReviewEntity reviewEntity = new ReviewEntity();
        reviewEntity.setId(review.getId());
        reviewEntity.setName(review.getName());
        reviewEntity.setUserInformation(review.getUserInformation());
        reviewEntity.setPassword(review.getPassword());
        reviewEntity.setContent(review.getContent());
        reviewEntity.setViews(review.getViews());
        reviewEntity.setLikes(review.getLikes());
        reviewEntity.setDislikes(review.getDislikes());
        reviewEntity.setCreatedAt(review.getCreatedAt());
        reviewEntity.setGender(review.getGender());
        reviewEntity.setAge(review.getAge());
        reviewEntity.setSleepFlag(review.getSleepFlag());
        reviewEntity.setResult(review.getResult());
        reviewEntity.setStatus(review.getStatus());
        return reviewEntity;
    }

    public Review toModel() {
        Review review = new Review();
        review.setId(id);
        review.setName(name);
        review.setUserInformation(userInformation);
        review.setPassword(password);
        review.setContent(content);
        review.setViews(views);
        review.setLikes(likes);
        review.setDislikes(dislikes);
        review.setCreatedAt(createdAt);
        review.setGender(gender);
        review.setAge(age);
        review.setSleepFlag(sleepFlag);
        review.setResult(result);
        review.setStatus(status);
        return review;
    }

}