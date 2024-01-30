package com.example.base.review.controller;

import com.example.base.reportable.domain.dto.ReportableDelete;
import com.example.base.reportable.utils.IpAddressUtils;
import com.example.base.review.controller.port.ReviewService;
import com.example.base.review.controller.request.ReviewCreateRequest;
import com.example.base.review.controller.response.ReviewInfoResponse;
import com.example.base.review.controller.response.ReviewResponse;
import com.example.base.review.domain.dto.ReviewSearch;
import com.example.base.web.annotation.IpAddress;
import com.example.base.web.dto.PageCreate;
import com.example.base.web.dto.PageResponse;
import io.swagger.v3.oas.annotations.Parameter;
import jakarta.validation.Valid;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.web.bind.annotation.*;

@Builder
@RestController
@RequestMapping("/reviews")
@RequiredArgsConstructor
public class ReviewController {

    private final ReviewService reviewService;
    private final IpAddressUtils ipAddressUtils;

    @PostMapping
    public void create(@Valid @RequestBody ReviewCreateRequest reviewCreateRequest, @Parameter(hidden = true) @IpAddress String ipAddress) {
        reviewService.create(reviewCreateRequest, ipAddressUtils.from(ipAddress));
    }

    @GetMapping("/{id}")
    public ReviewInfoResponse getInfo(@PathVariable Long id) {
        return reviewService.get(id);
    }

    @GetMapping("/list")
    public PageResponse<ReviewResponse> getPagination(@Valid @ParameterObject ReviewSearch reviewSearch, @ParameterObject PageCreate pageCreate) {
        return reviewService.getPagination(pageCreate, reviewSearch);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id, @Valid @RequestBody ReportableDelete reportableDelete) {
        reviewService.delete(reportableDelete, id);
    }
}