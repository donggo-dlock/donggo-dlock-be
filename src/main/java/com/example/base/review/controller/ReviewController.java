package com.example.base.review.controller;

import com.example.base.review.controller.port.ReviewService;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Builder
@RestController
@RequestMapping("/foods")
@RequiredArgsConstructor
public class ReviewController {

    private final ReviewService reviewService;
}