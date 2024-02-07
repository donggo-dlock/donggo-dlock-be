package com.example.base.report.service;

import com.example.base.comment.controller.port.CommentService;
import com.example.base.food.controller.port.FoodService;
import com.example.base.report.domain.ReferenceType;
import com.example.base.report.controller.port.ReportService;
import com.example.base.report.domain.dto.ReportCreate;
import com.example.base.report.service.port.ReportRepository;
import com.example.base.reportable.domain.ActiveStatus;
import com.example.base.reportable.domain.Reportable;
import com.example.base.review.controller.port.ReviewService;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Objects;


@Service
@Builder
@RequiredArgsConstructor
public class ReportServiceImpl implements ReportService {

    private final ReportRepository reportRepository;
    private final CommentService commentService;
    private final FoodService foodService;
    private final ReviewService reviewService;

    @Override
    public void create(ReportCreate reportCreate, String ipAddress) {
        Reportable reportable = getReportable(reportCreate.referenceType(), reportCreate.referenceId());
        if (ActiveStatus.ACTIVE.equals(reportable.getStatus()))
            throw new IllegalArgumentException("삭제된 게시물에 대한 신고는 불가능합니다.");
//        reportRepository.save(reportCreate.toReport(reportable, ipAddress));
    }

    private Reportable getReportable(ReferenceType referenceType, Long referenceId) {
        if (Objects.isNull(referenceType) || Objects.isNull(referenceId)) {
            throw new IllegalArgumentException("참조 타입과 참조 ID는 필수입니다.");
        }

        return switch (referenceType) {
            case COMMENT -> commentService.get(referenceId);
            case FOOD -> foodService.get(referenceId);
            case REVIEW -> reviewService.get(referenceId);
            default -> throw new IllegalArgumentException("존재하지 않는 댓글 참조 타입입니다.");
        };
    }
}