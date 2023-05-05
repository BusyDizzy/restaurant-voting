package com.antontkatch.restaurant.service;

import com.antontkatch.restaurant.model.Dish;
import com.antontkatch.restaurant.repository.DishRepository;
import com.antontkatch.restaurant.repository.MenuRepository;
import com.antontkatch.restaurant.repository.RestaurantRepository;
import com.antontkatch.restaurant.to.DishTo;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.List;


@AllArgsConstructor
@Service
public class DishService {

    private final ModelMapper modelMapper;

    private final DishRepository dishRepository;

    private final MenuRepository menuRepository;

    private final RestaurantRepository restaurantRepository;

    public Dish get(int id, int menuId, int restaurantId) {
        checkRestaurant(restaurantId);
        checkMenu(menuId);
        return dishRepository.getExisted(id, menuId);
    }

    public List<Dish> getAll(int menuId, int restaurantId) {
        checkRestaurant(restaurantId);
        checkMenu(menuId);
        return dishRepository.getAll(menuId);//.stream().map(dish -> modelMapper.map(dish, DishTo.class)).toList();
    }

    public void delete(int id, int menuId, int restaurantId) {
        checkRestaurant(restaurantId);
        checkMenu(menuId);
        dishRepository.deleteExisted(id, menuId);
    }


    @Transactional
    public Dish save(Dish dish, int menuId, int restaurantId) {
        checkRestaurant(restaurantId);
        checkMenu(menuId);
        Assert.notNull(dish, "dish must not be null");
        if (!dish.isNew() && dishRepository.getExisted(dish.id(), menuId) == null) {
            return null;
        }
        dish.setMenu(menuRepository.getReferenceById(menuId));
        return dishRepository.save(dish);
    }

    public Dish convertDishToDish(DishTo dishTo) {
        return modelMapper.map(dishTo, Dish.class);
    }

    private void checkRestaurant(int restaurantId) {
        restaurantRepository.getExisted(restaurantId);
    }

    private void checkMenu(int menuId) {
        menuRepository.getExisted(menuId);
    }
}
