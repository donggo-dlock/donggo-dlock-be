package com.example.base.comment.service;


import com.example.base.comment.controller.port.CommentService;
import com.example.base.comment.controller.request.CommentCreateRequest;
import com.example.base.comment.controller.response.CommentResponse;
import com.example.base.comment.domain.Comment;
import com.example.base.comment.domain.ReferenceType;
import com.example.base.comment.service.port.CommentRepository;
import com.example.base.commentable.domain.Commentable;
import com.example.base.common.service.port.ClockHolder;
import com.example.base.common.service.port.PasswordHolder;
import com.example.base.food.controller.port.FoodService;
import com.example.base.reportable.domain.dto.ReportableDelete;
import com.example.base.review.controller.port.ReviewService;
import com.example.base.web.dto.PageCreate;
import com.example.base.web.dto.SliceResponse;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

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
        Commentable commentable = getCommentable(request);
        commentRepository.save(Comment.from(commentable, request, clockHolder, passwordHolder, userInformation));
    }

    private Commentable getCommentable(CommentCreateRequest request) {
        Commentable commentable;
        if (ReferenceType.FOOD.equals(request.referenceType())) {
            commentable = foodService.get(request.referenceId());
        } else if (ReferenceType.REVIEW.equals(request.referenceType())) {
            commentable = reviewService.get(request.referenceId());
        } else{
            throw new IllegalArgumentException("존재하지 않는 댓글 참조 타입입니다.");
        }
        return commentable;
    }

    @Override
    public SliceResponse<CommentResponse> get(ReferenceType referenceType, Long referenceId, PageCreate pageCreate) {
        return null;
    }

    @Override
    public Comment get(Long id) {
        return commentRepository.get(id);
    }

    @Override
    public void delete(Long id, ReportableDelete reportableDelete) {
        //TODO: 댓글 삭제
    }
}