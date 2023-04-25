package com.antontkatch.restaurant.service;

import com.antontkatch.restaurant.model.Menu;
import com.antontkatch.restaurant.repository.DishRepository;
import com.antontkatch.restaurant.repository.MenuRepository;
import com.antontkatch.restaurant.repository.RestaurantRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.List;


@AllArgsConstructor
@Service
public class MenuService {

    private final MenuRepository menuRepository;

    private final RestaurantRepository restaurantRepository;

    private final DishRepository dishRepository;

    public Menu get(int id, int restaurantId) {
        return menuRepository.getExisted(id, restaurantId);
    }

    public Menu getWithDishes(int id, int restaurantId) {
        Menu menu = menuRepository.getExisted(id, restaurantId);
        Assert.notNull(menu, "menu must not be null");
        menu.setDishes(dishRepository.getAll(id)); // Null Pointer fix needed!
        return menu;
    }

    public Menu getTodayMenu(int restaurantId) {
        Menu menu = menuRepository.getCurrent(restaurantId);
        Assert.notNull(menu, "menu must not be null");
        menu.setDishes(dishRepository.getAll(menu.getId()));
        return menu;
    }

    public boolean delete(int id, int restaurantId) {
        return menuRepository.delete(id, restaurantId) != 0;
    }

    public List<Menu> getAll(int restaurantId) {
        return menuRepository.getAll(restaurantId);
    }


    @Transactional
    public Menu save(Menu menu, int restaurantId) {
        Assert.notNull(menu, "menu must not be null");
        if (!menu.isNew() && menuRepository.getExisted(menu.id(), restaurantId) == null) {
            return null;
        }
        menu.setRestaurant(restaurantRepository.getReferenceById(restaurantId));
        return menuRepository.save(menu);
    }
}
