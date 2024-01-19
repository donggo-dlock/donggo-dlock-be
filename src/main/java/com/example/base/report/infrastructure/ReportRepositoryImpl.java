package com.example.base.report.infrastructure;

import com.example.base.common.infrastructure.repository.BaseRepository;
import com.example.base.report.service.port.ReportRepository;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.stereotype.Repository;
@Repository
public class ReportRepositoryImpl extends BaseRepository<ReportEntity, Long> implements ReportRepository {

    public ReportRepositoryImpl(JPAQueryFactory queryFactory) {
        super(queryFactory);
    }
}
