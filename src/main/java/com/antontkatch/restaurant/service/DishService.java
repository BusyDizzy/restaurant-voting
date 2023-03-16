package com.antontkatch.restaurant.service;

import com.antontkatch.restaurant.model.Dish;
import com.antontkatch.restaurant.repository.DishRepository;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.List;

import static com.antontkatch.restaurant.util.ValidationUtil.checkNotFoundWithId;

@Service
public class DishService {

    private final DishRepository repository;

    public DishService(DishRepository repository) {
        this.repository = repository;
    }

    public Dish get(int id, int menuId) {
        return checkNotFoundWithId(repository.get(id, menuId), id);
    }

    public void delete(int id, int menuId) {
        checkNotFoundWithId(repository.delete(id, menuId), id);
    }

    public List<Dish> getAll(int menuId) {
        return repository.getAll(menuId);
    }

    public void update(Dish dish, int menuId) {
        Assert.notNull(dish, "dish must not be null");
        checkNotFoundWithId(repository.save(dish, menuId), dish.id());
    }

    public Dish create(Dish dish, int menuId) {
        Assert.notNull(dish, "dish must not be null");
        return repository.save(dish, menuId);
    }
}
