package com.antontkatch.restaurant.service;

import com.antontkatch.restaurant.model.Menu;
import com.antontkatch.restaurant.model.Restaurant;
import com.antontkatch.restaurant.repository.DishRepository;
import com.antontkatch.restaurant.repository.RestaurantRepository;
import com.antontkatch.restaurant.to.RestaurantTo;
import lombok.AllArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@Service
public class RestaurantService {
    private final RestaurantRepository restaurantRepository;

    private final DishRepository dishRepository;

    @Transactional(readOnly = true)
    @Cacheable("restaurants")
    public List<RestaurantTo> findAllWithTodayMenu() {
        List<RestaurantTo> restaurantTos = new ArrayList<>();
        for (Restaurant restaurant : restaurantRepository.findAllWithTodayMenus()) {
            Menu menu = restaurant.getMenus().stream()
                    .filter(m -> m.getDate().isEqual(LocalDate.now()))
                    .findFirst().get();
            menu.setDishes(dishRepository.getAll(menu.getId()));
            restaurantTos.add(new RestaurantTo(restaurant.getId(), restaurant.getName(), restaurant.getAddress(), menu));
        }
        return restaurantTos;
    }

    public List<Restaurant> getAll() {
        return restaurantRepository.findAll();
    }

    public Restaurant get(int id) {
        return restaurantRepository.getExisted(id);
    }

    public void delete(int id) {
        restaurantRepository.deleteExisted(id);
    }

    public Restaurant save(Restaurant restaurant) {
        return restaurantRepository.save(restaurant);
    }
}
