package com.example.base.review.infrastructure;

import com.example.base.common.exception.ResourceNotFoundException;
import com.example.base.common.infrastructure.repository.BaseRepository;
import com.example.base.review.domain.Review;
import com.example.base.review.service.port.ReviewRepository;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class ReviewRepositoryImpl extends BaseRepository<ReviewEntity, Long> implements ReviewRepository {

    private static final String REVIEW_MESSAGE = "review";
    QReviewEntity qReview = QReviewEntity.reviewEntity;

    public ReviewRepositoryImpl(JPAQueryFactory queryFactory) {
        super(queryFactory);
    }


    @Override
    public void save(Review review) {
        save(ReviewEntity.from(review));
    }

    @Override
    public Review get(Long id) {
        ReviewEntity reviewEntity = Optional.ofNullable(
                selectFrom(qReview)
                .where(qReview.id.eq(id))
                .fetchOne())
                .orElseThrow(() -> new ResourceNotFoundException(REVIEW_MESSAGE, id));

        return reviewEntity.toModel();
    }
}
