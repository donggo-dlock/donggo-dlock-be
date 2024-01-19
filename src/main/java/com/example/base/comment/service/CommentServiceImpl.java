package com.example.base.comment.service;


import com.example.base.comment.controller.port.CommentService;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@Builder
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {
}