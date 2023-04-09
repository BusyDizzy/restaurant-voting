package com.antontkatch.restaurant.repository.datajpa;

import com.antontkatch.restaurant.model.Menu;
import com.antontkatch.restaurant.repository.MenuRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.List;

import static com.antontkatch.restaurant.util.validation.ValidationUtil.checkNotFoundWithId;

@Repository
@Transactional(readOnly = true)
public class DataJpaMenuRepository implements MenuRepository {

    private final CrudMenuRepository crudMenuRepository;

    private final CrudRestaurantRepository crudRestaurantRepository;

    private final CrudDishRepository crudDishRepository;

    public DataJpaMenuRepository(CrudMenuRepository crudMenuRepository, CrudRestaurantRepository crudRestaurantRepository, CrudDishRepository crudDishRepository) {
        this.crudMenuRepository = crudMenuRepository;
        this.crudRestaurantRepository = crudRestaurantRepository;
        this.crudDishRepository = crudDishRepository;
    }

    @Override
    public Menu get(int id, int restaurantId) {
        Menu menu = crudMenuRepository.findById(id)
                .filter(m -> m.getRestaurant().getId() == restaurantId)
                .orElse(null);
        checkNotFoundWithId(menu, id);
        return menu;
    }

    @Override
    public Menu getWithDishes(int id, int restaurantId) {
        Menu menu = crudMenuRepository.findById(id)
                .filter(m -> m.getRestaurant().getId() == restaurantId)
                .orElse(null);
        Assert.notNull(menu, "menu must not be null");
        checkNotFoundWithId(menu, id);
        menu.setDishes(crudDishRepository.getAll(id)); // Null Pointer fix needed!
        return menu;
    }

    @Override
    public Menu getTodayMenu(int restaurantId) {
        Menu menu = crudMenuRepository.getCurrent(restaurantId);
        Assert.notNull(menu, "menu must not be null");
        checkNotFoundWithId(menu, menu.id());
        menu.setDishes(crudDishRepository.getAll(menu.getId()));
        return menu;
    }

    @Override
    public List<Menu> getAll(int restaurantId) {
        return crudMenuRepository.getAll(restaurantId);
    }

    @Override
    public boolean delete(int id, int restaurantId) {
        checkNotFoundWithId(crudMenuRepository.delete(id, restaurantId) != 0, id);
        return true;
    }

    @Override
    @Transactional
    public Menu save(Menu menu, int restaurantId) {
        Assert.notNull(menu, "menu must not be null");
        if (!menu.isNew() && get(menu.id(), restaurantId) == null) {
            return null;
        }
        menu.setRestaurant(crudRestaurantRepository.getReferenceById(restaurantId));
        checkNotFoundWithId(crudMenuRepository.save(menu), menu.id());
        return menu;
    }
}
