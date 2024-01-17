package com.example.base.post.service;


import com.example.base.common.service.port.ClockHolder;
import com.example.base.post.controller.port.PostService;
import com.example.base.post.controller.response.PostResponse;
import com.example.base.post.domain.Post;
import com.example.base.post.domain.PostCreate;
import com.example.base.post.domain.PostUpdate;
import com.example.base.post.service.port.PostRepository;
import com.example.base.user.domain.User;
import com.example.base.user.domain.exception.ResourceNotFoundException;
import com.example.base.user.service.port.UserRepository;
import com.example.base.web.dto.PageCreate;
import com.example.base.web.dto.PageResponse;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Builder
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {

    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final ClockHolder clockHolder;

    public Post getById(long id) {
        return postRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Posts", id));
    }

    public PageResponse<PostResponse> getAll(PageCreate pageCreate) {
        PageResponse pageResponse = postRepository.findAll(pageCreate);
        List<Post> postList = pageResponse.getContent();
        return pageResponse.updateType(postList.stream().map(PostResponse::from).toList());
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public Post create(PostCreate postCreate) {
        User user =userRepository.getById(postCreate.writerId());
        return postRepository.save(Post.from(postCreate, user, clockHolder));
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public Post update(long id, PostUpdate postUpdate) {
        Post post = getById(id);
        post = post.update(postUpdate, clockHolder);
        return postRepository.save(post);
    }
}