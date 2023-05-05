package com.antontkatch.restaurant.web.menu;

import com.antontkatch.restaurant.error.NotFoundException;
import com.antontkatch.restaurant.model.Menu;
import com.antontkatch.restaurant.service.MenuService;
import com.antontkatch.restaurant.util.JsonUtil;
import com.antontkatch.restaurant.web.AbstractControllerTest;
import com.antontkatch.restaurant.web.restaurant.RestaurantController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

import static com.antontkatch.restaurant.MenuTestData.getNew;
import static com.antontkatch.restaurant.MenuTestData.getUpdated;
import static com.antontkatch.restaurant.MenuTestData.*;
import static com.antontkatch.restaurant.RestaurantTestData.NOT_FOUND;
import static com.antontkatch.restaurant.RestaurantTestData.*;
import static com.antontkatch.restaurant.TestUtil.userHttpBasic;
import static com.antontkatch.restaurant.UserTestData.admin;
import static com.antontkatch.restaurant.UserTestData.user;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class MenuRestControllerTest extends AbstractControllerTest {

    static final String REST_URL = RestaurantController.REST_URL + "/" + RESTAURANT1_ID + "/menus";

    @Autowired
    private MenuService service;

    @Test
    void getAllForRestaurant() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL)
                .with(userHttpBasic(admin)))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(MENU_MATCHER.contentJson(menu1, menu4));
    }

    @Test
    void getAllForRestaurantNotFound() throws Exception {
        perform(MockMvcRequestBuilders.get(RestaurantController.REST_URL + "/" + NOT_FOUND + "/menus")
                .with(userHttpBasic(admin)))
                .andExpect(status().isNotFound())
                .andDo(print());
    }

    @Test
    void get() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL + "/" + MENU1_ID)
                .with(userHttpBasic(admin)))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(MENU_MATCHER.contentJson(menu1));
    }

    @Test
    void getTodayMenu() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL + "/today")
                .with(userHttpBasic(user)))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(MENU_WITH_DISHES_MATCHER.contentJson(menu4));
    }

    @Test
    void delete() throws Exception {
        perform(MockMvcRequestBuilders.delete(REST_URL + "/" + MENU1_ID)
                .with(userHttpBasic(admin)))
                .andDo(print())
                .andExpect(status().isNoContent());
        assertThrows(NotFoundException.class, () -> service.get(MENU1_ID, RESTAURANT1_ID));
    }

    @Test
    void deleteNotFound() throws Exception {
        perform(MockMvcRequestBuilders.delete(REST_URL + "/" + NOT_FOUND)
                .with(userHttpBasic(admin)))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    void update() throws Exception {
        Menu updated = getUpdated();
        perform(MockMvcRequestBuilders.put(REST_URL + "/" + MENU1_ID)
                .with(userHttpBasic(admin))
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(updated)))
                .andExpect(status().isNoContent());

        MENU_MATCHER.assertMatch(service.get(MENU1_ID, RESTAURANT1_ID), updated);
    }

    @Test
    void updateInvalidMenuId() throws Exception {
        Menu updated = getUpdated();
        perform(MockMvcRequestBuilders.put(REST_URL + "/" + MENU2_ID)
                .with(userHttpBasic(admin))
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(updated)))
                .andDo(print())
                .andExpect(status().isUnprocessableEntity());
    }

    @Test
    @Transactional(propagation = Propagation.NEVER)
    void updateWithDuplicate() throws Exception {
        Menu updated = getUpdated();
        updated.setDate(LocalDate.now());
        perform(MockMvcRequestBuilders.put(REST_URL + "/" + MENU1_ID)
                .with(userHttpBasic(admin))
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(updated)))
                .andDo(print())
                .andExpect(status().isConflict());
    }

    @Test
    void getUnauth() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL + "/" + MENU1_ID))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void postUnauth() throws Exception {
        Menu newMenu = getNew();
        perform(MockMvcRequestBuilders.post(REST_URL)
                .with(userHttpBasic(user))
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(newMenu)))
                .andExpect(status().isForbidden());
    }

    @Test
    void createWithLocation() throws Exception {
        Menu newMenu = getNew();
        ResultActions action = perform(MockMvcRequestBuilders.post(REST_URL)
                .with(userHttpBasic(admin))
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(newMenu)))
                .andDo(print())
                .andExpect(status().isCreated());

        Menu created = MENU_MATCHER.readFromJson(action);
        int newId = created.id();
        newMenu.setId(newId);
        MENU_MATCHER.assertMatch(created, newMenu);
        MENU_MATCHER.assertMatch(service.get(newId, RESTAURANT1_ID), newMenu);
    }

    @Test
    void createDuplicate() throws Exception {
        Menu newMenu = new Menu(null, LocalDate.now(), restaurant1);
        perform(MockMvcRequestBuilders.post(REST_URL)
                .with(userHttpBasic(admin))
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(newMenu)))
                .andDo(print())
                .andExpect(status().isConflict());
    }

    @Test
    void createInvalid() throws Exception {
        Menu newMenu = new Menu((Integer) null, null, null);
        perform(MockMvcRequestBuilders.post(REST_URL)
                .with(userHttpBasic(admin))
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(newMenu)))
                .andDo(print())
                .andExpect(status().isUnprocessableEntity());
    }

    @Test
    void updateInvalid() throws Exception {
        Menu updated = getUpdated();
        perform(MockMvcRequestBuilders.put(REST_URL + "/" + RESTAURANT1_ID)
                .with(userHttpBasic(admin))
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(updated)))
                .andExpect(status().isNoContent());

        MENU_MATCHER.assertMatch(service.get(MENU1_ID, RESTAURANT1_ID), updated);
    }
}
