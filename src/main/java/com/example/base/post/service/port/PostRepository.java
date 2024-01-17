package com.example.base.post.service.port;


import com.example.base.post.domain.Post;
import com.example.base.web.dto.PageCreate;
import com.example.base.web.dto.PageResponse;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

public interface PostRepository {
    Optional<Post> findById(long id);

    PageResponse<Post> findAll(PageCreate pageCreate);

    @Transactional(propagation = Propagation.MANDATORY)
    Post save(Post post);
}
