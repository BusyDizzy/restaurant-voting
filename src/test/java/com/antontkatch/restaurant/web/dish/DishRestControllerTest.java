package com.antontkatch.restaurant.web.dish;

import com.antontkatch.restaurant.error.NotFoundException;
import com.antontkatch.restaurant.model.Dish;
import com.antontkatch.restaurant.service.DishService;
import com.antontkatch.restaurant.util.JsonUtil;
import com.antontkatch.restaurant.web.AbstractControllerTest;
import com.antontkatch.restaurant.web.restaurant.RestaurantController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.List;

import static com.antontkatch.restaurant.DishTestData.*;
import static com.antontkatch.restaurant.MenuTestData.MENU1_ID;
import static com.antontkatch.restaurant.RestaurantTestData.NOT_FOUND;
import static com.antontkatch.restaurant.RestaurantTestData.RESTAURANT1_ID;
import static com.antontkatch.restaurant.TestUtil.userHttpBasic;
import static com.antontkatch.restaurant.UserTestData.admin;
import static com.antontkatch.restaurant.UserTestData.user;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class DishRestControllerTest extends AbstractControllerTest {

    @Autowired
    private DishService service;
    static final String REST_URL = RestaurantController.REST_URL + "/" + RESTAURANT1_ID + "/menus/" + MENU1_ID + "/dishes";

    @Test
    void getAll() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL)
                .with(userHttpBasic(user)))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(DISH_MATCHER.contentJson(List.of(dish3, dish2, dish1)));
    }

    @Test
    void get() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL + "/" + DISH1_ID)
                .with(userHttpBasic(user)))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(DISH_MATCHER.contentJson(dish1));
    }

    @Test
    void getRestaurantNotFound() throws Exception {
        perform(MockMvcRequestBuilders.get(RestaurantController.REST_URL + "/" + NOT_FOUND + "/menus/" + MENU1_ID + "/dishes/" + DISH1_ID)
                .with(userHttpBasic(user)))
                .andExpect(status().isNotFound())
                .andDo(print());
    }

    @Test
    void update() throws Exception {
        Dish updated = getUpdated();
        perform(MockMvcRequestBuilders.put(REST_URL + "/" + DISH1_ID)
                .with(userHttpBasic(admin))
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(updated)))
                .andExpect(status().isNoContent());

        DISH_MATCHER.assertMatch(service.get(DISH1_ID, MENU1_ID, RESTAURANT1_ID), updated);
    }

    @Test
    void updateInvalidId() throws Exception {
        Dish updated = getUpdated();
        perform(MockMvcRequestBuilders.put(REST_URL + "/" + DISH2_ID)
                .with(userHttpBasic(admin))
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(updated)))
                .andExpect(status().isUnprocessableEntity());
    }

    @Test
    void updateNotFound() throws Exception {
        Dish updated = getUpdated();
        perform(MockMvcRequestBuilders.put(REST_URL + "/" + NOT_FOUND)
                .with(userHttpBasic(admin))
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(updated)))
                .andDo(print())
                .andExpect(status().isUnprocessableEntity());
    }

    @Test
    void updateInvalid() throws Exception {
        Dish updated = new Dish(null, null, null);
        perform(MockMvcRequestBuilders.put(REST_URL + "/" + DISH1_ID)
                .with(userHttpBasic(admin))
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(updated)))
                .andExpect(status().isUnprocessableEntity());
    }

    @Test
    void createWithLocation() throws Exception {
        Dish newDish = getNew();
        ResultActions action = perform(MockMvcRequestBuilders.post(REST_URL)
                .with(userHttpBasic(admin))
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(newDish)))
                .andDo(print())
                .andExpect(status().isCreated());

        Dish created = DISH_MATCHER.readFromJson(action);
        int newId = created.id();
        newDish.setId(newId);
        DISH_MATCHER.assertMatch(created, newDish);
        DISH_MATCHER.assertMatch(service.get(newId, MENU1_ID, RESTAURANT1_ID), newDish);
    }

    @Test
    void createInvalid() throws Exception {
        Dish newDish = new Dish(null, null, null);
        perform(MockMvcRequestBuilders.post(REST_URL)
                .with(userHttpBasic(admin))
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(newDish)))
                .andDo(print())
                .andExpect(status().isUnprocessableEntity());
    }

    @Test
    void createDuplicate() throws Exception {
        Dish newDish = new Dish(null, "Pepperoni Pizza", 10);
        perform(MockMvcRequestBuilders.post(REST_URL)
                .with(userHttpBasic(admin))
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(newDish)))
                .andDo(print())
                .andExpect(status().isConflict());
    }

    @Test
    void delete() throws Exception {
        perform(MockMvcRequestBuilders.delete(REST_URL + "/" + DISH1_ID)
                .with(userHttpBasic(admin)))
                .andDo(print())
                .andExpect(status().isNoContent());
        assertThrows(NotFoundException.class, () -> service.get(DISH1_ID, MENU1_ID, RESTAURANT1_ID));
    }

    @Test
    void deleteNotFound() throws Exception {
        perform(MockMvcRequestBuilders.delete(REST_URL + "/" + NOT_FOUND)
                .with(userHttpBasic(admin)))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    void getUnauth() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL + DISH1_ID))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void postUnauth() throws Exception {
        Dish newDish = getNew();
        perform(MockMvcRequestBuilders.post(REST_URL)
                .with(userHttpBasic(user))
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(newDish)))
                .andDo(print())
                .andExpect(status().isForbidden());
    }
}
