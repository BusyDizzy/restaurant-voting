package com.antontkatch.restaurant.service;

import com.antontkatch.restaurant.model.Dish;
import com.antontkatch.restaurant.repository.DishRepository;
import com.antontkatch.restaurant.repository.MenuRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.List;


@AllArgsConstructor
@Service
public class DishService {

    private final DishRepository dishRepository;

    private final MenuRepository menuRepository;

    public Dish get(int id, int menuId) {
        return dishRepository.getExisted(id, menuId);
    }

    public List<Dish> getAll(int menuId) {
        return dishRepository.getAll(menuId);
    }

    public boolean delete(int id, int menuId) {
        return dishRepository.delete(id, menuId) != 0;
    }


    @Transactional
    public Dish save(Dish dish, int menuId) {
        Assert.notNull(dish, "dish must not be null");
        if (!dish.isNew() && get(dish.id(), menuId) == null) {
            return null;
        }
        dish.setMenu(menuRepository.getReferenceById(menuId));
        return dishRepository.save(dish);
    }
}
