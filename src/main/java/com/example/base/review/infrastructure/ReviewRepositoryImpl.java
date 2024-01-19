package com.example.base.review.infrastructure;

import com.example.base.common.infrastructure.repository.BaseRepository;
import com.example.base.review.service.port.ReviewRepository;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.stereotype.Repository;
@Repository
public class ReviewRepositoryImpl extends BaseRepository<ReviewEntity, Long> implements ReviewRepository {

    public ReviewRepositoryImpl(JPAQueryFactory queryFactory) {
        super(queryFactory);
    }
}
