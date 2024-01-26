package com.example.base.mock;

import com.example.base.food.domain.Food;
import com.example.base.review.service.port.ReviewRepository;
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
    public void increase(Long id, Boolean recommendationFlag) {

    }
}
