package com.example.base.comment.controller;

import com.example.base.comment.controller.port.CommentService;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@Builder
@RestController
@RequestMapping("/foods")
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;
}