package com.example.base.food.service.port;


import com.example.base.food.domain.Food;
import com.example.base.food.domain.dto.FoodSearch;
import com.example.base.web.dto.PageCreate;
import com.example.base.web.dto.PageResponse;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

public interface FoodRepository {
    @Transactional(propagation = Propagation.MANDATORY)
    void save(Food food);

    @Transactional(propagation = Propagation.MANDATORY, readOnly = true)
    Food get(String name);

    @Transactional(propagation = Propagation.MANDATORY, readOnly = true)
    Food get(Long id);

    @Transactional(propagation = Propagation.MANDATORY, readOnly = true)
    PageResponse<Food> getPage(PageCreate pageCreate, FoodSearch foodSearch);

    @Transactional(propagation = Propagation.MANDATORY)
    void delete(Food food);
}
