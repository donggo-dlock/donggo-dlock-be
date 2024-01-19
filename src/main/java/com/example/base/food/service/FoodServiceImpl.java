package com.example.base.food.service;


import com.example.base.food.controller.port.FoodService;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@Builder
@RequiredArgsConstructor
public class FoodServiceImpl implements FoodService {
}