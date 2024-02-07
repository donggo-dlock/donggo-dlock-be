package com.example.base.report.domain.dto;

import com.example.base.report.domain.ReferenceType;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;

@Schema(name = "ReportCreate", description = "Report create request")
public record ReportCreate(
        @Schema(description = "신고할 게시물 유형", allowableValues = {"COMMENT", "FOOD", "REVIEW"})
        @NotBlank
        ReferenceType referenceType,

        @Schema(description = "신고할 게시물 ID", example = "1")
        @Positive
        Long referenceId
) {
}
