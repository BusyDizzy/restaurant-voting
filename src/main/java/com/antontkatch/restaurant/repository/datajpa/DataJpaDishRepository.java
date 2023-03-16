package com.antontkatch.restaurant.repository.datajpa;

import com.antontkatch.restaurant.model.Dish;
import com.antontkatch.restaurant.repository.DishRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public class DataJpaDishRepository implements DishRepository {

    private final CrudDishRepository crudDishRepository;

    private final CrudMenuRepository crudMenuRepository;

    public DataJpaDishRepository(CrudDishRepository crudDishRepository, CrudMenuRepository crudMenuRepository) {
        this.crudDishRepository = crudDishRepository;
        this.crudMenuRepository = crudMenuRepository;
    }

    @Override
    public Dish get(int id, int menuId) {
        return crudDishRepository.findById(id)
                .filter(dish -> dish.getMenu().getId() == menuId)
                .orElse(null);
    }

    @Override
    public List<Dish> getAll(int menuId) {
        return crudDishRepository.getAll(menuId);
    }

    @Override
    public boolean delete(int id, int menuId) {
        return crudDishRepository.delete(id, menuId) != 0;
    }

    @Override
    @Transactional
    public Dish save(Dish dish, int menuId) {
        if (!dish.isNew() && get(dish.id(), menuId) == null) {
            return null;
        }
        dish.setMenu(crudMenuRepository.getReferenceById(menuId));
        return crudDishRepository.save(dish);
    }
}
