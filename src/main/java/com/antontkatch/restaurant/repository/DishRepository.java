package com.antontkatch.restaurant.repository;

import com.antontkatch.restaurant.error.NotFoundException;
import com.antontkatch.restaurant.model.Dish;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional(readOnly = true)
public interface DishRepository extends BaseRepository<Dish> {

    @Transactional
    @Modifying
    @Query("DELETE FROM Dish d WHERE d.id=:id AND d.menu.id=:menuId")
    int delete(@Param("id") int id, @Param("menuId") int menuId);

    @Cacheable("dishes")
    @Query("SELECT d FROM Dish d WHERE d.menu.id=:menuId")
    List<Dish> getAll(@Param("menuId") int menuId);

    default void deleteExisted(int id, int menuId) {
        if (delete(id, menuId) == 0) {
            throw new NotFoundException("Entity with id=" + id + " not found");
        }
    }

    @Cacheable("dish")
    default Dish getExisted(int id, int menuId) {
        return findById(id)
                .filter(dish -> dish.getMenu().getId() == menuId)
                .orElseThrow(() -> new NotFoundException("Entity with id=" + id + " not found"));
    }
}
