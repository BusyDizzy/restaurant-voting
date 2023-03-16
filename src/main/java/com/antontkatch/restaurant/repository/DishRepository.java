package com.antontkatch.restaurant.repository;

import com.antontkatch.restaurant.model.Dish;

import java.util.List;

public interface DishRepository {

    Dish save(Dish dish, int menuId);

    boolean delete(int id, int menuId);

    Dish get(int id, int menuId);

    List<Dish> getAll(int menuId);
}
