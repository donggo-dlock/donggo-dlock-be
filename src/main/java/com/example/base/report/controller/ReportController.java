package com.example.base.report.controller;

import com.example.base.report.controller.port.ReportService;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Builder
@RestController
@RequestMapping("/foods")
@RequiredArgsConstructor
public class ReportController {

    private final ReportService reportService;
}