package com.example.base.post.controller;

import com.example.base.post.controller.port.PostService;
import com.example.base.post.controller.response.PostResponse;
import com.example.base.post.domain.PostCreate;
import com.example.base.post.domain.PostUpdate;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Builder
@RestController
@RequestMapping("/api/posts")
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    @GetMapping("/{id}")
    public ResponseEntity<PostResponse> getById(@PathVariable long id) {
        return ResponseEntity
            .ok()
            .body(PostResponse.from(postService.getById(id)));
    }

    @PutMapping("/{id}")
    public ResponseEntity<PostResponse> update(@PathVariable long id, @RequestBody PostUpdate postUpdate) {
        return ResponseEntity
            .ok()
            .body(PostResponse.from(postService.update(id, postUpdate)));
    }

    @PostMapping
    public ResponseEntity<PostResponse> create(@RequestBody PostCreate postCreate) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(PostResponse.from(postService.create(postCreate)));
    }
}