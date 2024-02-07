package com.example.base.comment.controller;

import com.example.base.comment.controller.port.CommentService;
import com.example.base.comment.controller.request.CommentCreateRequest;
import com.example.base.comment.controller.response.CommentResponse;
import com.example.base.comment.domain.dto.CommentSearch;
import com.example.base.reportable.domain.dto.ReportableDelete;
import com.example.base.web.annotation.IpAddress;
import com.example.base.web.dto.SliceResponse;
import io.swagger.v3.oas.annotations.Parameter;
import jakarta.validation.Valid;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.web.bind.annotation.*;

@Builder
@RestController
@RequestMapping("/comments")
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    @PostMapping
    public void create(@Valid @RequestBody CommentCreateRequest request, @IpAddress @Parameter(hidden = true) String ipAddress) {
        commentService.create(request, ipAddress);
    }

    @GetMapping
    public SliceResponse<CommentResponse> getList(@Valid @ParameterObject CommentSearch commentSearch) {
        return commentService.getList(commentSearch);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id") Long id, @Valid @RequestBody ReportableDelete commentDeleteRequest) {
        commentService.delete(id, commentDeleteRequest);
    }

}