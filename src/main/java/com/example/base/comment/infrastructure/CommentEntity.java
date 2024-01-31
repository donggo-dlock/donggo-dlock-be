package com.example.base.comment.infrastructure;

import com.example.base.comment.domain.Comment;
import com.example.base.comment.domain.ReferenceType;
import com.example.base.commentable.domain.Commentable;
import com.example.base.food.domain.Food;
import com.example.base.food.infrastructure.FoodEntity;
import com.example.base.reportable.domain.ActiveStatus;
import com.example.base.review.domain.Review;
import com.example.base.review.infrastructure.ReviewEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "comments")
public class CommentEntity {

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

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private ActiveStatus status;

    @Column(name = "created_at")
    private Long createdAt;

    @Column(name = "reference_type")
    @Enumerated(EnumType.STRING)
    private ReferenceType referenceType;

    @ManyToOne
    @JoinColumn(name = "food_id")
    private FoodEntity foodReference;

    @ManyToOne
    @JoinColumn(name = "review_id")
    private ReviewEntity reviewReference;

    public static CommentEntity from(Comment comment) {
        CommentEntity commentEntity = new CommentEntity();
        commentEntity.setId(comment.getId());
        commentEntity.setName(comment.getName());
        commentEntity.setUserInformation(comment.getUserInformation());
        commentEntity.setPassword(comment.getPassword());
        commentEntity.setContent(comment.getContent());
        commentEntity.setStatus(comment.getStatus());
        commentEntity.setCreatedAt(comment.getCreatedAt());
        final Commentable reference = comment.getReference();
        if (reference instanceof Food food) {
            commentEntity.setReferenceType(ReferenceType.FOOD);
            commentEntity.setFoodReference(FoodEntity.from(food));
        } else if (reference instanceof Review review) {
            commentEntity.setReferenceType(ReferenceType.REVIEW);
            commentEntity.setReviewReference(ReviewEntity.from(review));
        }
        return commentEntity;
    }

    public Comment toModel() {
        Comment comment = new Comment();
        comment.setId(id);
        comment.setName(name);
        comment.setUserInformation(userInformation);
        comment.setPassword(password);
        comment.setContent(content);
        comment.setStatus(status);
        comment.setCreatedAt(createdAt);
        comment.setReferenceType(referenceType);
        if (foodReference != null) {
            comment.setReference(foodReference.toModel());
        } else if (reviewReference != null) {
            comment.setReference(reviewReference.toModel());
        }
        return comment;
    }

}