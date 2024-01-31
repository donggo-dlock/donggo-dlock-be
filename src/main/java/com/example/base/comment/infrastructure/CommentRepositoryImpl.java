package com.example.base.comment.infrastructure;

import com.example.base.comment.domain.Comment;
import com.example.base.comment.service.port.CommentRepository;
import com.example.base.commentable.domain.Commentable;
import com.example.base.common.infrastructure.repository.BaseRepository;
import com.example.base.food.domain.Food;
import com.example.base.review.domain.Review;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.stereotype.Repository;

import java.util.List;

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
        return selectFrom(qComment)
                .where(qComment.id.eq(id))
                .fetchOne()
                .toModel();
    }

    @Override
    public void delete(Comment comment) {
        delete(CommentEntity.from(comment));
    }

    @Override
    public List<Comment> getByReference(Long lastId, Commentable reference, int size) {
        return selectFrom(qComment)
                .where(eqReference(reference)
                        .and(qComment.id.gt(lastId)))
                .limit(size+1L)
                .fetch()
                .stream().map(CommentEntity::toModel).toList();
    }

    private BooleanExpression eqReference(final Commentable commentable) {
        if (commentable instanceof Food)
            return qComment.foodReference.id.eq(commentable.getId());
        if (commentable instanceof Review)
            return qComment.reviewReference.id.eq(commentable.getId());
        throw new IllegalArgumentException("존재하지 않는 댓글 참조 타입입니다.");
    }


}
