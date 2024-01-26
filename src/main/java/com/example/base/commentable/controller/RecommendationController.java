package com.example.base.commentable.controller;

import com.example.base.commentable.domain.dto.RecommendationCreate;
import com.example.base.commentable.service.RecommendationServiceImpl;
import com.example.base.reportable.utils.IpAddressUtils;
import com.example.base.web.annotation.IpAddress;
import io.swagger.v3.oas.annotations.Parameter;
import jakarta.validation.Valid;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Builder
@RestController
@RequestMapping("/recommendations")
@RequiredArgsConstructor
@Validated
public class RecommendationController {
    private final IpAddressUtils ipAddressUtils;
    private final RecommendationServiceImpl recommendationServiceImpl;

    @PostMapping
    public void recommendContent(@Valid @ParameterObject RecommendationCreate recommendationCreate, @Parameter(hidden = true) @IpAddress String ipAddress){
        recommendationServiceImpl.create(ipAddressUtils.from(ipAddress), recommendationCreate);
    }
}
