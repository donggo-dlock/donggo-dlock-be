package com.example.base.post.infrastructure;

import com.example.base.common.infrastructure.repository.BaseRepository;
import com.example.base.post.domain.Post;
import com.example.base.post.service.port.PostRepository;
import com.example.base.user.infrastructure.QUserEntity;
import com.example.base.web.dto.PageCreate;
import com.example.base.web.dto.PageResponse;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
@Repository
public class PostRepositoryImpl extends BaseRepository<PostEntity, Long> implements PostRepository {
    QUserEntity user = QUserEntity.userEntity;
    QPostEntity post = QPostEntity.postEntity;

    public PostRepositoryImpl(JPAQueryFactory queryFactory) {
        super(queryFactory);
    }

    @Override
    public Optional<Post> findById(long id) {
        PostEntity postEntity = selectFrom(post)
                .where(post.id.eq(id))
                .fetchOne();
        return Optional.ofNullable(postEntity.toModel());
    }

    @Override
    public PageResponse<Post> findAll(PageCreate pageCreate) {
        List<Post> postList = getPaginationContent(selectFrom(post), pageCreate).stream().map(PostEntity::toModel).toList();
        Long totalCount = getTotalCount(selectFrom(post));

        return PageResponse.of(postList, pageCreate, totalCount);
    }

    @Override
    public Post save(Post post) {
        return save(PostEntity.from(post)).toModel();
    }
}
