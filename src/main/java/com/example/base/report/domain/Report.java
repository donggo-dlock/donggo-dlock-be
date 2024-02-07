package com.example.base.report.domain;

import com.example.base.reportable.domain.Reportable;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class Report {
    private long id;
    private ReportStatus status;
    private String erInformation;
    private Long createdAt;
    private ReferenceType referenceType;
    private Reportable reference;


}
