package com.antontkatch.restaurant.repository.datajpa;

import com.antontkatch.restaurant.model.Restaurant;
import com.antontkatch.restaurant.repository.RestaurantRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.List;

import static com.antontkatch.restaurant.util.validation.ValidationUtil.checkNotFoundWithId;

@Repository
@Transactional(readOnly = true)
public class DataJpaRestaurantRepository implements RestaurantRepository {

    private final CrudRestaurantRepository crudRepository;

    public DataJpaRestaurantRepository(CrudRestaurantRepository crudRepository) {
        this.crudRepository = crudRepository;
    }

    @Override
    @Transactional
    public Restaurant save(Restaurant restaurant) {
        Assert.notNull(crudRepository.save(restaurant), "restaurant must not be null");
        return restaurant;
    }

    @Override
    public boolean delete(int id) {
        checkNotFoundWithId(crudRepository.delete(id) != 0, id);
        return true;
    }

    @Override
    public Restaurant get(int id) {
        Restaurant restaurant = crudRepository.findById(id).orElse(null);
        checkNotFoundWithId(restaurant, id);
        return restaurant;
    }

    @Override
    public List<Restaurant> getAll() {
        return crudRepository.findAll();
    }
}
