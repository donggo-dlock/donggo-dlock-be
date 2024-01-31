package com.example.base.comment.controller.port;

import com.example.base.comment.controller.request.CommentCreateRequest;
import com.example.base.comment.controller.response.CommentResponse;
import com.example.base.comment.domain.Comment;
import com.example.base.comment.domain.dto.CommentSearch;
import com.example.base.reportable.domain.dto.ReportableDelete;
import com.example.base.web.dto.PageCreate;
import com.example.base.web.dto.SliceResponse;

public interface CommentService {
    public void create(CommentCreateRequest request, String userInformation);
    public SliceResponse<CommentResponse> getList(CommentSearch commentSearch, PageCreate pageCreate);
    public Comment get(Long id);
    public void delete(Long id, ReportableDelete reportableDelete);
}
