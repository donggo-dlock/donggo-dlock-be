package com.example.base.mock;

import com.example.base.food.domain.Food;
import com.example.base.review.domain.Review;
import com.example.base.review.domain.dto.ReviewSearch;
import com.example.base.review.service.port.ReviewRepository;
import com.example.base.web.dto.PageCreate;
import com.example.base.web.dto.PageResponse;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

@Slf4j
public class FakeReviewRepository implements ReviewRepository {
    private final AtomicLong idGenerator = new AtomicLong(1L);
    private final List<Food> data = Collections.synchronizedList(new ArrayList<>());

    @Override
    public void save(Review review) {

    }

    @Override
    public Review get(Long id) {
        return null;
    }

    @Override
    public void delete(Review review) {

    }

    @Override
    public PageResponse<Review> getPage(PageCreate pageCreate, ReviewSearch reviewSearch) {
        return null;
    }
}
