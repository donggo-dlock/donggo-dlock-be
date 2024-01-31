package com.example.base.comment.service.port;


import com.example.base.comment.domain.Comment;
import com.example.base.commentable.domain.Commentable;
import com.example.base.web.dto.PageCreate;

import java.util.List;

public interface CommentRepository {
    void save(Comment comment);
    Comment get(Long id);
    void delete(Comment comment);
    List<Comment> getByReference(Long lastId, Commentable reference, PageCreate pageCreate);
}
