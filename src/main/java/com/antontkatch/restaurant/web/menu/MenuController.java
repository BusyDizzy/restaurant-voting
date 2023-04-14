package com.antontkatch.restaurant.web.menu;

import com.antontkatch.restaurant.model.Menu;
import com.antontkatch.restaurant.repository.MenuRepository;
import com.antontkatch.restaurant.web.restaurant.RestaurantController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
@RequestMapping(value = MenuController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
public class MenuController {

    protected final Logger log = LoggerFactory.getLogger(getClass());
    public static final String REST_URL = RestaurantController.REST_URL + "/" + "{restaurantId}/menus";

    @Autowired
    private MenuRepository repository;

    @GetMapping
    public List<Menu> getAll(@PathVariable int restaurantId) {
        log.info("getAll");
        return repository.getAll(restaurantId);
    }

    @GetMapping("/{menuId}")
    public Menu get(@PathVariable int menuId, @PathVariable int restaurantId) {
        log.info("get menu {} of restaurant {}", menuId, restaurantId);
        return repository.get(menuId, restaurantId);
    }

    @GetMapping("/{menuId}/with-dishes")
    public Menu getWithDishes(@PathVariable int menuId, @PathVariable int restaurantId) {
        log.info("get menu {} of restaurant {}", menuId, restaurantId);
        return repository.getWithDishes(menuId, restaurantId);
    }

    @GetMapping("/today")
    public Menu getTodayMenu(@PathVariable int restaurantId) {
        log.info("get today's menu of restaurant {}", restaurantId);
        return repository.getTodayMenu(restaurantId);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Menu> create(@RequestBody Menu menu, @PathVariable int restaurantId) {
        String URL = RestaurantController.REST_URL + "/" + restaurantId + "/menus";
        log.info("create {} of restaurant {}", menu, restaurantId);
        checkNew(menu);
        Menu created = repository.save(menu, restaurantId);
        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(URL + "/{id}")
                .buildAndExpand(created.getId()).toUri();
        return ResponseEntity.created(uriOfNewResource).body(created);
    }

    @DeleteMapping("/{menuId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable int menuId, @PathVariable int restaurantId) {
        log.info("delete menu {} of restaurant {}", menuId, restaurantId);
        repository.delete(menuId, restaurantId);
    }

    @PutMapping(value = "/{menuId}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@RequestBody Menu menu, @PathVariable int restaurantId, @PathVariable int menuId) {
        log.info("update {} with restaurant id={}", menu, restaurantId);
        assureIdConsistent(menu, menuId);
        repository.save(menu, restaurantId);
    }
}
