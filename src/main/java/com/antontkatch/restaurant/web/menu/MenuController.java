package com.antontkatch.restaurant.web.menu;

import com.antontkatch.restaurant.model.Menu;
import com.antontkatch.restaurant.service.MenuService;
import com.antontkatch.restaurant.to.MenuTo;
import com.antontkatch.restaurant.web.restaurant.RestaurantController;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
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

@Tag(name = "Menu Controller")
@RestController
@RequestMapping(value = MenuController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
@Slf4j
public class MenuController {

    public static final String REST_URL = RestaurantController.REST_URL + "/{restaurantId}/menus";
    @Autowired
    private MenuService service;

    @GetMapping
    public List<Menu> getAll(@PathVariable int restaurantId) {
        log.info("getAll");
        return service.getAll(restaurantId);
    }

    @GetMapping("/{menuId}")
    public Menu get(@PathVariable int menuId, @PathVariable int restaurantId) {
        log.info("get menu {} of restaurant {}", menuId, restaurantId);
        return service.get(menuId, restaurantId);
    }

    @GetMapping("/{menuId}/with-dishes")
    public Menu getWithDishes(@PathVariable int menuId, @PathVariable int restaurantId) {
        log.info("get menu {} of restaurant {}", menuId, restaurantId);
        return service.getWithDishes(menuId, restaurantId);
    }

    @GetMapping("/today")
    public Menu getTodayMenu(@PathVariable int restaurantId) {
        log.info("get today's menu of restaurant {}", restaurantId);
        return service.getTodayMenu(restaurantId);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    @CacheEvict(value = "menu", allEntries = true)
    public ResponseEntity<Menu> create(@Valid @RequestBody MenuTo menuTo, @PathVariable int restaurantId) {
        String URL = RestaurantController.REST_URL + "/" + restaurantId + "/menus";
        log.info("create {} of restaurant {}", menuTo, restaurantId);
        checkNew(menuTo);
        Menu created = service.save(service.convertMenuToMenu(menuTo), restaurantId);
        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(URL + "/{id}")
                .buildAndExpand(created.getId()).toUri();
        return ResponseEntity.created(uriOfNewResource).body(created);
    }

    @DeleteMapping("/{menuId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @CacheEvict(value = "menu", allEntries = true)
    public void delete(@PathVariable int menuId, @PathVariable int restaurantId) {
        log.info("delete menu {} of restaurant {}", menuId, restaurantId);
        service.delete(menuId, restaurantId);
    }

    @PutMapping(value = "/{menuId}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @CacheEvict(value = "menu", allEntries = true)
    public void update(@Valid @RequestBody MenuTo menuTo, @PathVariable int restaurantId, @PathVariable int menuId) {
        log.info("update {} with restaurant id={}", menuTo, restaurantId);
        assureIdConsistent(menuTo, menuId);
        service.save(service.convertMenuToMenu(menuTo), restaurantId);
    }
}
