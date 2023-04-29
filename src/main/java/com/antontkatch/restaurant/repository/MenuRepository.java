package com.antontkatch.restaurant.repository;

import com.antontkatch.restaurant.error.NotFoundException;
import com.antontkatch.restaurant.model.Menu;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional(readOnly = true)
public interface MenuRepository extends BaseRepository<Menu> {

    @Transactional
    @Modifying
    @Query("DELETE FROM Menu m WHERE m.id=:id AND m.restaurant.id=:restaurantId")
    @CacheEvict(value = "menu", allEntries = true)
    int delete(@Param("id") int id, @Param("restaurantId") int restaurantId);

    @Cacheable("menu")
    @Query("SELECT m FROM Menu m WHERE m.restaurant.id=:restaurantId")
    List<Menu> getAll(@Param("restaurantId") int restaurantId);

    @Query(value = "SELECT * FROM menu WHERE restaurant_id=:restaurantId and date_added = CURRENT_DATE", nativeQuery = true)
    Menu getCurrent(@Param("restaurantId") int restaurantId);

    default Menu getExisted(int id, int restaurantId) {
        return findById(id)
                .filter(menu -> menu.getRestaurant().getId() == restaurantId)
                .orElseThrow(() -> new NotFoundException("Entity with id=" + id + " not found"));
    }
}
