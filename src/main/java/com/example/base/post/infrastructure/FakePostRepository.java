package com.example.base.post.infrastructure;


import com.example.base.post.domain.Post;
import com.example.base.post.service.port.PostRepository;
import com.example.base.web.dto.PageCreate;
import com.example.base.web.dto.PageResponse;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

public class FakePostRepository implements PostRepository {
    private final AtomicLong idGenerator = new AtomicLong(1L);
    private final List<Post> data = Collections.synchronizedList(new ArrayList<>());


    @Override
    public Optional<Post> findById(long id) {
        return data.stream()
                .filter(item -> item.id().equals(id))
                .findAny();
    }

    @Override
    public PageResponse<Post> findAll(PageCreate pageCreate) {
        return null;
    }

    @Override
    public Post save(Post post) {
        if (post.id() == null || post.id() == 0) {
            Post newPost = Post.builder()
                    .id(idGenerator.getAndIncrement())
                    .content(post.content())
                    .writer(post.writer())
                    .createdAt(post.createdAt())
                    .modifiedAt(post.modifiedAt())
                    .build();
            data.add(newPost);
            return newPost;
        }
        data.removeIf(item -> item.id().equals(post.id()));
        data.add(post);
        return post;
    }
}
