package com.antontkatch.restaurant.web.dish;

import com.antontkatch.restaurant.model.Dish;
import com.antontkatch.restaurant.repository.DishRepository;
import com.antontkatch.restaurant.web.menu.MenuController;
import com.antontkatch.restaurant.web.restaurant.RestaurantController;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
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
public class DishController {

    static final String REST_URL = MenuController.REST_URL + "/" + "{menuId}/dishes";

    @Autowired
    private DishRepository repository;

    @GetMapping
    public List<Dish> getAll(@PathVariable int menuId) {
        log.info("getAll");
        return repository.getAll(menuId);
    }

    @GetMapping("/{id}")
    public Dish get(@PathVariable int id, @PathVariable int menuId) {
        log.info("get dish {} of menu {}", id, menuId);
        return repository.get(id, menuId);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Dish> create(@RequestBody Dish dish, @PathVariable int menuId) {
        String URL = RestaurantController.REST_URL + "/" + menuId + "/menus";
        log.info("create dish {} of menu {}", dish, menuId);
        checkNew(dish);
        Dish created = repository.save(dish, menuId);
        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(URL + "/{id}")
                .buildAndExpand(created.getId()).toUri();
        return ResponseEntity.created(uriOfNewResource).body(created);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable int id, @PathVariable int menuId) {
        log.info("delete dish {} of menu {}", menuId, menuId);
        repository.delete(id, menuId);
    }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@RequestBody Dish dish, @PathVariable int menuId, @PathVariable int id) {
        log.info("update dish {} with menu id={}", dish, menuId);
        assureIdConsistent(dish, id);
        repository.save(dish, menuId);
    }
}