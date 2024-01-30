package com.example.base.mock;

import com.example.base.common.exception.ResourceNotFoundException;
import com.example.base.food.domain.Food;
import com.example.base.food.domain.dto.FoodSearch;
import com.example.base.food.service.port.FoodRepository;
import com.example.base.reportable.domain.ActiveStatus;
import com.example.base.web.dto.PageCreate;
import com.example.base.web.dto.PageResponse;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

@Slf4j
public class FakeFoodRepository implements FoodRepository {
    private final AtomicLong idGenerator = new AtomicLong(1L);
    private final List<Food> data = Collections.synchronizedList(new ArrayList<>());

    @Override
    public void save(Food food) {
        if (food.getId() == null || food.getId() == 0) {
            Food newFood = new Food();
            newFood.setId(idGenerator.getAndIncrement());
            newFood.setName(food.getName());
            newFood.setDaysBeforeTest(food.getDaysBeforeTest());
            newFood.setUserInformation(food.getUserInformation());
            newFood.setPassword(food.getPassword());
            newFood.setContent(food.getContent());
            newFood.setMainIngredient(food.getMainIngredient());
            newFood.setViews(food.getViews());
            newFood.setLikes(food.getLikes());
            newFood.setDislikes(food.getDislikes());
            newFood.setCreatedAt(food.getCreatedAt());
            newFood.setStatus(food.getStatus());
            data.add(newFood);
            return;
        }
        data.removeIf(item -> item.getId().equals(food.getId()));
        data.add(food);
    }

    @Override
    public Food get(String name) {
        return data.stream()
                .filter(item -> item.getName().equals(name))
                .findAny()
                .orElseThrow(() -> new ResourceNotFoundException("Food", name));
    }

    @Override
    public Food get(Long id) {
        return data.stream()
                .filter(item -> item.getId().equals(id))
                .filter(item -> item.getStatus().equals(ActiveStatus.ACTIVE))
                .findAny()
                .orElseThrow(() -> new ResourceNotFoundException("Food", id));
    }

    @Override
    public PageResponse<Food> getPage(PageCreate pageCreate, FoodSearch foodSearch) {
        List<Food> filteredData = data.stream()
                .filter(item -> item.getStatus().equals(ActiveStatus.ACTIVE))
                .filter(item -> foodSearch.keyword().isEmpty()? true : item.getName().contains(foodSearch.keyword()))
                .filter(item -> item.getDaysBeforeTest() == foodSearch.daysBeforeTest())
                .sorted(Comparator.<Food>comparingInt(item -> "views".equals(foodSearch.sortBy()) ? item.getViews() : 0)
                        .thenComparingInt(item -> "likes".equals(foodSearch.sortBy()) ? item.getLikes() : 0)
                        .thenComparingLong(Food::getId)
                        .reversed())
                .collect(Collectors.toList());

        int total = filteredData.size();
        filteredData = filteredData.stream()
                .skip(pageCreate.getOffset())
                .limit(pageCreate.getSize())
                .collect(Collectors.toList());

        return PageResponse.of(filteredData, pageCreate, total);
    }

    @Override
    public void delete(Food food) {
        data.removeIf(item -> item.getId().equals(food.getId()));
    }
}
