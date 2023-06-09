package com.antontkatch.restaurant.service;

import com.antontkatch.restaurant.model.Menu;
import com.antontkatch.restaurant.repository.DishRepository;
import com.antontkatch.restaurant.repository.MenuRepository;
import com.antontkatch.restaurant.repository.RestaurantRepository;
import com.antontkatch.restaurant.to.MenuTo;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.List;


@AllArgsConstructor
@Service
public class MenuService {

    private final ModelMapper modelMapper;

    private final MenuRepository menuRepository;

    private final RestaurantRepository restaurantRepository;

    private final DishRepository dishRepository;

    public Menu get(int id, int restaurantId) {
        checkRestaurant(restaurantId);
        return menuRepository.getExisted(id, restaurantId);
    }

    public Menu getWithDishes(int id, int restaurantId) {
        checkRestaurant(restaurantId);
        Menu menu = menuRepository.getExisted(id, restaurantId);
        Assert.notNull(menu, "menu must not be null");
        menu.setDishes(dishRepository.getAll(id));
        return menu;
    }

    public Menu getTodayMenu(int restaurantId) {
        checkRestaurant(restaurantId);
        Menu menu = menuRepository.getCurrent(restaurantId);
        Assert.notNull(menu, "menu must not be null");
        menu.setDishes(dishRepository.getAll(menu.getId()));
        return menu;
    }

    public void delete(int id, int restaurantId) {
        checkRestaurant(restaurantId);
        menuRepository.deleteExisted(id, restaurantId);
    }

    public List<Menu> getAll(int restaurantId) {
        checkRestaurant(restaurantId);
        return menuRepository.getAll(restaurantId);
    }


    @Transactional
    public Menu save(Menu menu, int restaurantId) {
        checkRestaurant(restaurantId);
        Assert.notNull(menu, "menu must not be null");
        if (!menu.isNew() && menuRepository.getExisted(menu.id(), restaurantId) == null) {
            return null;
        }
        menu.setRestaurant(restaurantRepository.getReferenceById(restaurantId));
        return menuRepository.save(menu);
    }

    public Menu convertMenuToMenu(MenuTo menuTo) {
        return modelMapper.map(menuTo, Menu.class);
    }

    private void checkRestaurant(int restaurantId) {
        restaurantRepository.getExisted(restaurantId);
    }
}
