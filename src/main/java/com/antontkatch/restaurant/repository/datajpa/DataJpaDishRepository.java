package com.antontkatch.restaurant.repository.datajpa;

import com.antontkatch.restaurant.model.Dish;
import com.antontkatch.restaurant.repository.DishRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.List;

import static com.antontkatch.restaurant.util.validation.ValidationUtil.checkNotFoundWithId;

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
        Dish dish = crudDishRepository.findById(id)
                .filter(d -> d.getMenu().getId() == menuId)
                .orElse(null);
        checkNotFoundWithId(dish, id);
        return dish;
    }

    @Override
    public List<Dish> getAll(int menuId) {
        return crudDishRepository.getAll(menuId);
    }

    @Override
    public boolean delete(int id, int menuId) {
        checkNotFoundWithId(crudDishRepository.delete(id, menuId) != 0, id);
        return true;
    }

    @Override
    @Transactional
    public Dish save(Dish dish, int menuId) {
        Assert.notNull(dish, "dish must not be null");
        if (!dish.isNew() && get(dish.id(), menuId) == null) {
            return null;
        }
        dish.setMenu(crudMenuRepository.getReferenceById(menuId));
        checkNotFoundWithId(crudDishRepository.save(dish), dish.id());
        return dish;
    }
}
