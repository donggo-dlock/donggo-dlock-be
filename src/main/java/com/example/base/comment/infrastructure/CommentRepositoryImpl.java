package com.example.base.comment.infrastructure;

import com.example.base.comment.service.port.CommentRepository;
import com.example.base.common.infrastructure.repository.BaseRepository;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.stereotype.Repository;
@Repository
public class CommentRepositoryImpl extends BaseRepository<CommentEntity, Long> implements CommentRepository {

    public CommentRepositoryImpl(JPAQueryFactory queryFactory) {
        super(queryFactory);
    }
}
