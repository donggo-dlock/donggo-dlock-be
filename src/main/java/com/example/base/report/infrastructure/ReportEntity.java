package com.example.base.report.infrastructure;

import com.example.base.comment.domain.Comment;
import com.example.base.comment.infrastructure.CommentEntity;
import com.example.base.common.exception.ResourceNotFoundException;
import com.example.base.food.domain.Food;
import com.example.base.food.infrastructure.FoodEntity;
import com.example.base.report.domain.ReferenceType;
import com.example.base.report.domain.Report;
import com.example.base.report.domain.ReportStatus;
import com.example.base.reportable.domain.Reportable;
import com.example.base.review.domain.Review;
import com.example.base.review.infrastructure.ReviewEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "reports")
public class ReportEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "erInformation", columnDefinition = "varchar(50)")
    private String erInformation;

    @Column(name = "edInformation", columnDefinition = "varchar(50)")
    private String edInformation;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private ReportStatus status;

    @Column(name = "created_at")
    private Long createdAt;

    @Enumerated(EnumType.STRING)
    @Column(name = "reference_type")
    private ReferenceType referenceType;

    @ManyToOne
    @JoinColumn(name = "comment_id")
    private CommentEntity commentReference;

    @ManyToOne
    @JoinColumn(name = "food_id")
    private FoodEntity foodReference;

    @ManyToOne
    @JoinColumn(name = "review_id")
    private ReviewEntity reviewReference;

    public static ReportEntity from(Report report) {
        ReportEntity reportEntity = new ReportEntity();
        reportEntity.setId(report.getId());
        reportEntity.setErInformation(report.getErInformation());
        reportEntity.setEdInformation(report.getEdInformation());
        reportEntity.setStatus(report.getStatus());
        reportEntity.setCreatedAt(report.getCreatedAt());
        reportEntity.setReferenceType(report.getReferenceType());
        final Reportable reportable = report.getReference();
        if (reportable instanceof Comment comment) {
            reportEntity.setReferenceType(ReferenceType.COMMENT);
            reportEntity.setCommentReference(CommentEntity.from(comment));
            return reportEntity;
        }
        if (reportable instanceof Food food) {
            reportEntity.setReferenceType(ReferenceType.FOOD);
            reportEntity.setFoodReference(FoodEntity.from(food));
            return reportEntity;
        }
        if (reportable instanceof Review review) {
            reportEntity.setReferenceType(ReferenceType.REVIEW);
            reportEntity.setReviewReference(ReviewEntity.from(review));
            return reportEntity;
        }
        throw new ResourceNotFoundException(report.getReferenceType().name(), " is not found");
    }

    public Report toModel() {
        Reportable reference = null;
        if (foodReference != null) {
            reference = foodReference.toModel();
        } else if (reviewReference != null) {
            reference = reviewReference.toModel();
        } else if (commentReference != null) {
            reference = commentReference.toModel();
        } else {
            throw new ResourceNotFoundException(referenceType.name(), " is not found");
        }

        return Report.builder()
                .id(id)
                .erInformation(erInformation)
                .edInformation(edInformation)
                .status(status)
                .createdAt(createdAt)
                .referenceType(referenceType)
                .reference(reference)
                .build();
    }

}