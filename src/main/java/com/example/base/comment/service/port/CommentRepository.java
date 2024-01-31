package com.example.base.comment.service.port;


import com.example.base.comment.domain.Comment;

public interface CommentRepository {
    void save(Comment comment);
    Comment get(Long id);
    void delete(Comment comment);
}
