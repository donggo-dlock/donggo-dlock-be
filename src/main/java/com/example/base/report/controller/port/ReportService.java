package com.example.base.report.controller.port;

import com.example.base.report.domain.dto.ReportCreate;

public interface ReportService {
    void create(ReportCreate reportCreate, String ipAddress);
}
