package com.example.base.post.controller.port;


import com.example.base.post.domain.Post;
import com.example.base.post.domain.PostCreate;
import com.example.base.post.domain.PostUpdate;

public interface PostService {
    Post getById(long id);
    Post create(PostCreate postCreate);
    Post update(long id, PostUpdate postUpdate);
}
