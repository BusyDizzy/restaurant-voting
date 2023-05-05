package com.antontkatch.restaurant.web.dish;

import com.antontkatch.restaurant.model.Dish;
import com.antontkatch.restaurant.service.DishService;
import com.antontkatch.restaurant.to.DishTo;
import com.antontkatch.restaurant.web.menu.MenuController;
import com.antontkatch.restaurant.web.restaurant.RestaurantController;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

import static com.antontkatch.restaurant.util.validation.ValidationUtil.assureIdConsistent;
import static com.antontkatch.restaurant.util.validation.ValidationUtil.checkNew;

@RestController
@RequestMapping(value = DishController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
@Slf4j
@AllArgsConstructor
@Tag(name = "Dish Controller")
public class DishController {

    static final String REST_URL = MenuController.REST_URL + "/{menuId}/dishes";

    @Autowired
    private final DishService service;

    @GetMapping
    public List<Dish> getAll(@PathVariable int menuId, @PathVariable int restaurantId) {
        log.info("getAll");
        return service.getAll(menuId, restaurantId);
    }

    @GetMapping("/{id}")
    public Dish get(@PathVariable int id, @PathVariable int menuId, @PathVariable int restaurantId) {
        log.info("get dish {} of menu {} and restaurant {}", id, menuId, restaurantId);
        return service.get(id, menuId, restaurantId);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    @CacheEvict(value = "dishes", allEntries = true)
    public ResponseEntity<Dish> create(@Valid @RequestBody DishTo dishTo, @PathVariable int restaurantId, @PathVariable int menuId) {
        String URL = RestaurantController.REST_URL + restaurantId + "/menus/" + menuId + "/dishes";
        log.info("create dish {} of menu {}", dishTo, menuId);
        checkNew(dishTo);
        Dish created = service.save(service.convertDishToDish(dishTo), menuId, restaurantId);
        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(URL + "/{id}")
                .buildAndExpand(created.getId()).toUri();
        return ResponseEntity.created(uriOfNewResource).body(created);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @CacheEvict(value = "dishes", allEntries = true)
    public void delete(@PathVariable int id, @PathVariable int menuId, @PathVariable int restaurantId) {
        log.info("delete dish {} of menu {}", menuId, menuId);
        service.delete(id, menuId, restaurantId);
    }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @CacheEvict(value = "dishes", allEntries = true)
    public void update(@Valid @RequestBody DishTo dishTo, @PathVariable int menuId, @PathVariable int id, @PathVariable int restaurantId) {
        log.info("update dish {} with menu id={}", dishTo, menuId);
        assureIdConsistent(dishTo, id);
        service.save(service.convertDishToDish(dishTo), menuId, restaurantId);
    }
}