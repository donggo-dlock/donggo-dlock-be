package com.example.base.food.infrastructure;

import com.example.base.common.exception.ResourceNotFoundException;
import com.example.base.common.infrastructure.repository.BaseRepository;
import com.example.base.food.domain.Food;
import com.example.base.food.domain.dto.FoodSearch;
import com.example.base.food.service.port.FoodRepository;
import com.example.base.reportable.domain.ActiveStatus;
import com.example.base.web.dto.PageCreate;
import com.example.base.web.dto.PageResponse;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class FoodRepositoryImpl extends BaseRepository<FoodEntity, Long> implements FoodRepository {

    QFoodEntity food = QFoodEntity.foodEntity;

    public static final String FOOD_MESSAGE = "food";

    public FoodRepositoryImpl(JPAQueryFactory queryFactory) {
        super(queryFactory);
    }

    @Override
    public void save(Food food) {
        save(FoodEntity.from(food));
    }

    @Override
    public Food get(String name) {
        FoodEntity foodEntity = Optional.ofNullable
                (selectFrom(food)
                .where(food.name.eq(name))
                .fetchOne())
                .orElseThrow(() -> new ResourceNotFoundException(FOOD_MESSAGE, name));

        return foodEntity.toModel();
    }

    @Override
    public Food get(Long id) {
        FoodEntity foodEntity = Optional.ofNullable
                        (selectFrom(food)
                                .where(food.id.eq(id).and(food.status.eq(ActiveStatus.ACTIVE)))
                                .fetchOne())
                .orElseThrow(() -> new ResourceNotFoundException(FOOD_MESSAGE, id));

        return foodEntity.toModel();
    }

    @Override
    public PageResponse<Food> getPage(PageCreate pageCreate, FoodSearch foodSearch) {
        List<Food> foodList = getPaginationContent(
                selectFrom(food)
                        .where(food.status.eq(ActiveStatus.ACTIVE),
                                eqKeyword(foodSearch.keyword()),
                                eqDaysBeforeTest(foodSearch.daysBeforeTest()))
                        .orderBy(getOrderSpecifierList(foodSearch.sortBy()))
                , pageCreate).stream().map(FoodEntity::toModel).toList();
        Long totalCount = getTotalCount(
                selectFrom(food)
                        .where(food.status.eq(ActiveStatus.ACTIVE),
                                eqKeyword(foodSearch.keyword()),
                                eqDaysBeforeTest(foodSearch.daysBeforeTest()))
        );
        return PageResponse.of(foodList, pageCreate, totalCount);
    }

    @Override
    public void delete(Food food) {
        delete(FoodEntity.from(food));
    }


    private BooleanExpression eqKeyword(final String keyword) {
        return keyword == null ? null : food.name.contains(keyword);
    }

    private BooleanExpression eqDaysBeforeTest(final int daysBeforeTest) {
        if (daysBeforeTest > 0 && daysBeforeTest < 4)
            return food.daysBeforeTest.eq(daysBeforeTest);
        if (daysBeforeTest == 0)
            return null;
        throw new IllegalArgumentException("daysBeforeTest는 3일 이하로 설정할 수 있습니다.");
    }

    private OrderSpecifier<?> getOrderSpecifierList(final String sortBy) {
        if (sortBy == null) {
            return food.id.desc();
        }

        return switch (sortBy) {
            case "VIEW" -> food.views.desc();
            case "LIKE" -> food.likes.desc();
            default -> food.id.desc();
        };
    }
}
