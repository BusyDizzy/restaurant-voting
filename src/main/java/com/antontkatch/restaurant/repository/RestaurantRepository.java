package com.antontkatch.restaurant.repository;

import com.antontkatch.restaurant.model.Restaurant;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional(readOnly = true)
public interface RestaurantRepository extends BaseRepository<Restaurant> {

    @EntityGraph(attributePaths = {"menus"}, type = EntityGraph.EntityGraphType.LOAD)
    @Query("SELECT r FROM Restaurant r LEFT JOIN Menu m on r.id= m.restaurant.id WHERE m.date = CURRENT_DATE")
    @Cacheable("restaurant")
    List<Restaurant> findAllWithTodayMenu();
}
