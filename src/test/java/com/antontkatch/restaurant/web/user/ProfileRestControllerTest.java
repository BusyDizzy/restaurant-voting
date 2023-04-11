package com.antontkatch.restaurant.web.user;

import com.antontkatch.restaurant.model.User;
import com.antontkatch.restaurant.repository.UserRepository;
import com.antontkatch.restaurant.to.UserTo;
import com.antontkatch.restaurant.util.JsonUtil;
import com.antontkatch.restaurant.util.UserUtil;
import com.antontkatch.restaurant.web.AbstractControllerTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static com.antontkatch.restaurant.TestUtil.mockAuthorize;
import static com.antontkatch.restaurant.TestUtil.userHttpBasic;
import static com.antontkatch.restaurant.UserTestData.*;
import static com.antontkatch.restaurant.web.user.ProfileRestController.REST_URL;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


class ProfileRestControllerTest extends AbstractControllerTest {

    @Autowired
    private UserRepository repository;

    @Test
    void get() throws Exception {
        mockAuthorize(user);
        perform(MockMvcRequestBuilders.get(REST_URL)
                .with(userHttpBasic(user)))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(USER_MATCHER.contentJson(user));
    }

    @Test
    void delete() throws Exception {
        mockAuthorize(user);
        perform(MockMvcRequestBuilders.delete(REST_URL)
                .with(userHttpBasic(user)))
                .andExpect(status().isNoContent());
        USER_MATCHER.assertMatch(repository.getAll(), admin, guest);
    }

    @Test
    void update() throws Exception {
        mockAuthorize(user);
        UserTo updatedTo = new UserTo(null, "newName", "user@yandex.ru", "newPassword");
        perform(MockMvcRequestBuilders.put(REST_URL).contentType(MediaType.APPLICATION_JSON)
                .with(userHttpBasic(user))
                .content(JsonUtil.writeValue(updatedTo)))
                .andDo(print())
                .andExpect(status().isNoContent());

        USER_MATCHER.assertMatch(repository.get(USER_ID), UserUtil.updateFromTo(new User(user), updatedTo));
    }
}