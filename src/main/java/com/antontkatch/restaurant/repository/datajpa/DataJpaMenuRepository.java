package com.antontkatch.restaurant.repository.datajpa;

import com.antontkatch.restaurant.model.Menu;
import com.antontkatch.restaurant.repository.MenuRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
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
        return crudMenuRepository.findById(id)
                .filter(menu -> menu.getRestaurant().getId() == restaurantId)
                .orElse(null);
    }

    @Override
    public Menu getWithDishes(int id, int restaurantId) {
        Menu menu = crudMenuRepository.findById(id)
                .filter(m -> m.getRestaurant().getId() == restaurantId)
                .orElse(null);
        menu.setDishes(crudDishRepository.getAll(id)); // Null Pointer fix!
        return menu;
    }

    @Override
    public Menu getTodayMenu(int restaurantId) {
        Menu menu = crudMenuRepository.getCurrent(restaurantId);
        if ( menu!=null) {
            menu.setDishes(crudDishRepository.getAll(menu.getId()));
            return menu;
        }
        return null;
    }

    @Override
    public List<Menu> getAll(int restaurantId) {
        return crudMenuRepository.getAll(restaurantId);
    }

    @Override
    public boolean delete(int id, int restaurantId) {
        return crudMenuRepository.delete(id, restaurantId) != 0;
    }

    @Override
    @Transactional
    public Menu save(Menu menu, int restaurantId) {
        if (!menu.isNew() && get(menu.id(), restaurantId) == null) {
            return null;
        }
        menu.setRestaurant(crudRestaurantRepository.getReferenceById(restaurantId));
        return crudMenuRepository.save(menu);
    }
}
