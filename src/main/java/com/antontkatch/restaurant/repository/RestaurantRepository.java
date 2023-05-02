package com.antontkatch.restaurant.repository;

import com.antontkatch.restaurant.model.Restaurant;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
public interface RestaurantRepository extends BaseRepository<Restaurant> {

//    @EntityGraph(attributePaths = {"menus"}, type = EntityGraph.EntityGraphType.FETCH )
//    @Query("SELECT r FROM Restaurant r LEFT OUTER JOIN FETCH Menu m on r.id= m.restaurant.id WHERE m.date = CURRENT_DATE")
//    @Cacheable("restaurants")
//     List<Restaurant> findAllWithTodayMenu();

}
