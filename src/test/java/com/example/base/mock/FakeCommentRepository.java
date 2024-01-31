package com.example.base.mock;

import com.example.base.comment.domain.Comment;
import com.example.base.comment.service.port.CommentRepository;
import com.example.base.commentable.domain.Commentable;
import com.example.base.common.exception.ResourceNotFoundException;
import com.example.base.web.dto.PageCreate;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

@Slf4j
public class FakeCommentRepository implements CommentRepository {
    private final AtomicLong idGenerator = new AtomicLong(1L);
    private final List<Comment> data = Collections.synchronizedList(new ArrayList<>());

    @Override
    public void save(Comment comment) {
        if (comment.getId() == null || comment.getId() == 0) {
            Comment newComment = new Comment();
            newComment.setId(idGenerator.getAndIncrement());
            newComment.setName(comment.getName());
            newComment.setReferenceType(comment.getReferenceType());
            newComment.setUserInformation(comment.getUserInformation());
            newComment.setPassword(comment.getPassword());
            newComment.setContent(comment.getContent());
            newComment.setReference(comment.getReference());
            newComment.setCreatedAt(comment.getCreatedAt());
            newComment.setStatus(comment.getStatus());
            data.add(newComment);
            return;
        }
        data.removeIf(item -> item.getId().equals(comment.getId()));
        data.add(comment);
    }

    @Override
    public Comment get(Long id) {
        return data.stream()
                .filter(item -> item.getId().equals(id))
                .findAny()
                .orElseThrow(() -> new ResourceNotFoundException("Comment", id));
    }

    @Override
    public void delete(Comment comment) {
        data.removeIf(item -> item.getId().equals(comment.getId()));
    }

    @Override
    public List<Comment> getByReference(Long lastId, Commentable reference, PageCreate pageCreate) {
        return data.stream()
                .filter(item -> item.getReference().equals(reference))
                .filter(item -> item.getId() > lastId)
                .limit(pageCreate.getSize()+1)
                .toList();
    }
}
