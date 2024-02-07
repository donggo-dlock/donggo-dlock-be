package com.example.base.report.controller;

import com.example.base.report.controller.port.ReportService;
import com.example.base.report.domain.dto.ReportCreate;
import com.example.base.web.annotation.IpAddress;
import io.swagger.v3.oas.annotations.Parameter;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/reports")
@RequiredArgsConstructor
public class ReportController {

    private final ReportService reportService;

    public void create(@RequestBody @Valid ReportCreate reportCreate,
                       @Parameter(hidden = true) @IpAddress String ipAddress){
        reportService.create(reportCreate, ipAddress);
    }
}