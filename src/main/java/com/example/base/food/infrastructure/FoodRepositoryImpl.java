package com.example.base.food.infrastructure;

import com.example.base.common.infrastructure.repository.BaseRepository;
import com.example.base.food.service.port.FoodRepository;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.stereotype.Repository;
@Repository
public class FoodRepositoryImpl extends BaseRepository<FoodEntity, Long> implements FoodRepository {

    public FoodRepositoryImpl(JPAQueryFactory queryFactory) {
        super(queryFactory);
    }
}
