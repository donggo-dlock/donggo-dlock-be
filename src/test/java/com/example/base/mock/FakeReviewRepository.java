package com.example.base.mock;

import com.example.base.common.exception.ResourceNotFoundException;
import com.example.base.food.domain.Food;
import com.example.base.reportable.domain.ActiveStatus;
import com.example.base.review.domain.Review;
import com.example.base.review.domain.dto.ReviewSearch;
import com.example.base.review.service.port.ReviewRepository;
import com.example.base.web.dto.PageCreate;
import com.example.base.web.dto.PageResponse;
import com.querydsl.core.types.dsl.BooleanExpression;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.annotation.Nullable;
import lombok.extern.slf4j.Slf4j;

import java.util.*;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

@Slf4j
public class FakeReviewRepository implements ReviewRepository {
    private final AtomicLong idGenerator = new AtomicLong(1L);
    private final List<Review> data = Collections.synchronizedList(new ArrayList<>());

    @Override
    public void save(Review review) {
        if (review.getId() == null || review.getId() == 0){
            Review newReview = new Review();
            newReview.setId(idGenerator.getAndIncrement());
            newReview.setName(review.getName());
            newReview.setContent(review.getContent());
            newReview.setUserInformation(review.getUserInformation());
            newReview.setPassword(review.getPassword());
            newReview.setGender(review.getGender());
            newReview.setAge(review.getAge());
            newReview.setSleepFlag(review.getSleepFlag());
            newReview.setResult(review.getResult());
            newReview.setCreatedAt(review.getCreatedAt());
            newReview.setLikes(review.getLikes());
            newReview.setDislikes(review.getDislikes());
            newReview.setViews(review.getViews());
            newReview.setStatus(review.getStatus());
            data.add(newReview);
            return;
        }
        data.removeIf(item -> item.getId().equals(review.getId()));
        data.add(review);
    }

    @Override
    public Review get(Long id) {
        return data.stream()
                .filter(item -> item.getId().equals(id))
                .filter(item -> item.getStatus().equals(ActiveStatus.ACTIVE))
                .findAny()
                .orElseThrow(() -> new ResourceNotFoundException("Review", id));
    }

    @Override
    public void delete(Review review) {
        data.removeIf(item -> item.getId().equals(review.getId()));
    }

    @Override
    public PageResponse<Review> getPage(PageCreate pageCreate, ReviewSearch reviewSearch) {
        int[] ageConditions = Arrays.stream(reviewSearch.age().split("#")).mapToInt(Integer::parseInt).toArray();
        String[] resultConditions = reviewSearch.result().split("#");

        List<Review> filteredData = data.stream()
            .filter(item -> item.getStatus().equals(ActiveStatus.ACTIVE))
            .filter(item -> reviewSearch.keyword().isEmpty()? true : item.getName().contains(reviewSearch.keyword()))
            .filter(item -> reviewSearch.gender() == '\u0000'? true: Objects.equals(reviewSearch.gender(), item.getGender()))
            .filter(item -> Objects.isNull(reviewSearch.sleepFlag())? true: Objects.equals(reviewSearch.sleepFlag(), item.getSleepFlag()))
            .filter(item -> Arrays.stream(ageConditions).anyMatch(age -> age == item.getAge()))
            .filter(item -> Arrays.stream(resultConditions).anyMatch(result -> result.equals(item.getResult())))
            .sorted(Comparator.<Review>comparingInt(item -> "views".equals(reviewSearch.sortBy()) ? item.getViews() : 0)
                    .thenComparingInt(item -> "likes".equals(reviewSearch.sortBy()) ? item.getLikes() : 0)
                    .thenComparingLong(Review::getId)
                    .reversed())
            .collect(Collectors.toList());

        int total = filteredData.size();
        filteredData = filteredData.stream()
                .skip(pageCreate.getOffset())
                .limit(pageCreate.getSize())
                .collect(Collectors.toList());

        return PageResponse.of(filteredData, pageCreate, total);
    }
}
