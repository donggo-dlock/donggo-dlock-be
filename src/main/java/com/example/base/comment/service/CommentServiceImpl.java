package com.example.base.comment.service;


import com.example.base.comment.controller.port.CommentService;
import com.example.base.comment.controller.request.CommentCreateRequest;
import com.example.base.comment.controller.response.CommentResponse;
import com.example.base.comment.domain.Comment;
import com.example.base.comment.domain.ReferenceType;
import com.example.base.comment.domain.dto.CommentSearch;
import com.example.base.comment.service.port.CommentRepository;
import com.example.base.commentable.domain.Commentable;
import com.example.base.common.service.port.ClockHolder;
import com.example.base.common.service.port.PasswordHolder;
import com.example.base.food.controller.port.FoodService;
import com.example.base.reportable.domain.dto.ReportableDelete;
import com.example.base.review.controller.port.ReviewService;
import com.example.base.web.dto.SliceResponse;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Builder
@RequiredArgsConstructor
@Transactional(propagation = Propagation.REQUIRES_NEW)
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;
    private final ClockHolder clockHolder;
    private final PasswordHolder passwordHolder;
    private final FoodService foodService;
    private final ReviewService reviewService;


    @Override
    public void create(CommentCreateRequest request, String userInformation) {
        Commentable commentable = getCommentable(request.referenceType(), request.referenceId());
        commentRepository.save(Comment.from(commentable, request, clockHolder, passwordHolder, userInformation));
    }

    @Override
    public SliceResponse<CommentResponse> getList(CommentSearch commentSearch) {
        Commentable commentable = getCommentable(commentSearch.referenceType(), commentSearch.referenceId());
        List<CommentResponse> commentResponses = commentRepository.getByReference(commentSearch.lastId(), commentable, commentSearch.size())
                .stream()
                .map(comment -> CommentResponse.from(comment, clockHolder))
                .toList();

        boolean hasNext = false;
        if (commentResponses.size() > commentSearch.size()) {
            hasNext = true;
            commentResponses = commentResponses.subList(0, commentResponses.size() - 1);
        }
        return SliceResponse.of(commentResponses, commentSearch.size(), hasNext);
    }

    private Commentable getCommentable(ReferenceType referenceType, Long referenceId) {
        Commentable commentable;
        if (ReferenceType.FOOD.equals(referenceType)) {
            commentable = foodService.get(referenceId);
        } else if (ReferenceType.REVIEW.equals(referenceType)) {
            commentable = reviewService.get(referenceId);
        } else{
            throw new IllegalArgumentException("존재하지 않는 댓글 참조 타입입니다.");
        }
        return commentable;
    }

    @Override
    public Comment get(Long id) {
        return commentRepository.get(id);
    }

    @Override
    public void delete(Long id, ReportableDelete reportableDelete) {
        Comment comment = commentRepository.get(id);
        passwordHolder.match(reportableDelete.password(), comment.getPassword());
        commentRepository.delete(comment);
    }
}