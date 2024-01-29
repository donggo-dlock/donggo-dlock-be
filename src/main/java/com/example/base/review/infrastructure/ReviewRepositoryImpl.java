package com.example.base.review.infrastructure;

import com.example.base.common.exception.ResourceNotFoundException;
import com.example.base.common.infrastructure.repository.BaseRepository;
import com.example.base.reportable.domain.ActiveStatus;
import com.example.base.review.domain.Review;
import com.example.base.review.domain.dto.ReviewSearch;
import com.example.base.review.service.port.ReviewRepository;
import com.example.base.web.dto.PageCreate;
import com.example.base.web.dto.PageResponse;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.stereotype.Repository;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
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

    @Override
    public void delete(Review review) {
        delete(ReviewEntity.from(review));
    }

    @Override
    public PageResponse<Review> getPage(PageCreate pageCreate, ReviewSearch reviewSearch) {
        List<Review> reviews = getPaginationContent(
                selectFrom(qReview)
                        .where(qReview.status.eq(ActiveStatus.ACTIVE),
                                eqKeyword(reviewSearch.keyword()),
                                eqAge(reviewSearch.age()),
                                eqGender(reviewSearch.gender()),
                                eqSleepFlag(reviewSearch.sleepFlag()),
                                eqResult(reviewSearch.result()))
                        .orderBy(getOrderSpecifierList(reviewSearch.sortBy()))
                , pageCreate).stream().map(ReviewEntity::toModel).toList();
        Long totalCount = getTotalCount(
                selectFrom(qReview)
                        .where(qReview.status.eq(ActiveStatus.ACTIVE),
                                eqKeyword(reviewSearch.keyword()),
                                eqAge(reviewSearch.age()),
                                eqGender(reviewSearch.gender()),
                                eqSleepFlag(reviewSearch.sleepFlag()),
                                eqResult(reviewSearch.result()))
        );
        return PageResponse.of(reviews, pageCreate, totalCount);
    }

    private BooleanExpression eqKeyword(final String keyword) {
        return keyword == null ? null : qReview.name.contains(keyword);
    }

    private BooleanExpression eqAge(final String age) {
        if (Objects.isNull(age))
            return null;
        int[] ageConditions = Arrays.stream(age.split("#")).mapToInt(Integer::parseInt).toArray();
        BooleanExpression ageCondition = qReview.age.eq(ageConditions[0]);
        for (int i = 1; i < ageConditions.length; i++) {
            ageCondition = ageCondition.or(qReview.age.eq(ageConditions[i]));
        }
        return ageCondition;
    }

    private BooleanExpression eqGender(final char gender) {
        if (gender == 'M' || gender == 'F')
            return qReview.gender.eq(gender);
        if (gender == '\u0000')
            return null;
        throw new IllegalArgumentException("gender는 M 또는 F로 설정할 수 있습니다.");
    }

    private BooleanExpression eqSleepFlag(final Boolean sleepFlag) {
        if (Objects.isNull(sleepFlag))
            return null;
        return qReview.sleepFlag.eq(sleepFlag);
    }

    private BooleanExpression eqResult(final String result) {
        if (Objects.isNull(result))
            return null;
        String[] resultConditions = result.split("#");
        BooleanExpression resultCondition = qReview.result.contains(resultConditions[0]);
        for (int i = 1; i < resultConditions.length; i++) {
            resultCondition = resultCondition.or(qReview.result.contains(resultConditions[i]));
        }
        return resultCondition;
    }

    private OrderSpecifier<?> getOrderSpecifierList(final String sortBy) {
        return switch (sortBy) {
            case "VIEW" -> qReview.views.desc();
            case "LIKE" -> qReview.likes.desc();
            default -> qReview.id.desc();
        };
    }
}
