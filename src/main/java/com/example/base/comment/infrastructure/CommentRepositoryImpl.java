package com.example.base.comment.infrastructure;

import com.example.base.comment.domain.Comment;
import com.example.base.comment.service.port.CommentRepository;
import com.example.base.common.infrastructure.repository.BaseRepository;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.stereotype.Repository;
@Repository
public class CommentRepositoryImpl extends BaseRepository<CommentEntity, Long> implements CommentRepository {
    QCommentEntity qComment = QCommentEntity.commentEntity;

    public CommentRepositoryImpl(JPAQueryFactory queryFactory) {
        super(queryFactory);
    }

    @Override
    public void save(Comment comment) {
        save(CommentEntity.from(comment));
    }

    @Override
    public Comment get(Long id) {
        return null;
    }

    @Override
    public void delete(Comment comment) {
        delete(CommentEntity.from(comment));
    }


}
