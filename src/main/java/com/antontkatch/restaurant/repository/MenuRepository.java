package com.antontkatch.restaurant.repository;

import com.antontkatch.restaurant.model.Menu;

import java.util.List;

public interface MenuRepository {

    Menu get(int id, int restaurantId);

    List<Menu> getAll(int restaurantId);

    boolean delete(int id, int restaurantId);

    Menu save(Menu menu, int restaurantId);

    Menu getWithDishes(int id, int restaurantId);

    Menu getTodayMenu(int restaurantId);
}
