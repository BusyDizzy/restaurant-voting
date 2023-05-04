package com.antontkatch.restaurant.web.vote;

import com.antontkatch.restaurant.UserTestData;
import com.antontkatch.restaurant.model.User;
import com.antontkatch.restaurant.model.Vote;
import com.antontkatch.restaurant.service.VoteService;
import com.antontkatch.restaurant.to.VoteTo;
import com.antontkatch.restaurant.util.JsonUtil;
import com.antontkatch.restaurant.web.AbstractControllerTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import static com.antontkatch.restaurant.RestaurantTestData.NOT_FOUND;
import static com.antontkatch.restaurant.RestaurantTestData.restaurant1;
import static com.antontkatch.restaurant.TestUtil.userHttpBasic;
import static com.antontkatch.restaurant.UserTestData.admin;
import static com.antontkatch.restaurant.UserTestData.user;
import static com.antontkatch.restaurant.VoteTestData.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class VoteRestControllerTest extends AbstractControllerTest {

    private static final String REST_URL = VoteController.REST_URL;

    @Autowired
    private VoteService service;

    @Test
    void getAll() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL)
                .with(userHttpBasic(user)))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(VOTE_TO_MATCHER.contentJson(service.convertVoteVoteTo(vote4), service.convertVoteVoteTo(vote5)));
    }

    @Test
    void createVote() throws Exception {
        VoteTo newVoteTo = new VoteTo();

        ResultActions action = perform(MockMvcRequestBuilders.post(REST_URL + "?restaurantId=" + restaurant1.getId())
                .with(userHttpBasic(user))
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(newVoteTo)))
                .andDo(print())
                .andExpect(status().isCreated());

        Vote created = VOTE_MATCHER.readFromJson(action);
        Vote newVote = new Vote(null, LocalDateTime.now(), restaurant1, user);
        int newId = created.id();
        newVote.setId(newId);
        VOTE_MATCHER.assertMatch(created, newVote);
        VOTE_MATCHER.assertMatch(service.get(newId, user.id()), newVote);
    }

    @Test
    void voteUpdateBeforeTimeIsExceeded() throws Exception {
        VoteTo newVoteTo = new VoteTo();
        newVoteTo.setDateTime(LocalDateTime.of(LocalDate.now(), LocalTime.of(10, 59)));
        ResultActions action = perform(MockMvcRequestBuilders.post(REST_URL + "?restaurantId=" + restaurant1.getId())
                .with(userHttpBasic(admin))
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(newVoteTo)))
                .andDo(print())
                .andExpect(status().isCreated());

        Vote created = VOTE_MATCHER.readFromJson(action);
        Vote newVote = getNewAdminVoteNow();
        int newId = created.id();
        newVote.setId(newId);
        VOTE_MATCHER.assertMatch(created, newVote);
        VOTE_MATCHER.assertMatch(service.get(newId, admin.id()), newVote);
    }

    @Test
    void voteUpdateAfterTimeIsExceeded() throws Exception {
        VoteTo newVoteTo = new VoteTo();
        newVoteTo.setDateTime(LocalDateTime.of(LocalDate.now(), LocalTime.of(11, 1)));

        perform(MockMvcRequestBuilders.post(REST_URL + "?restaurantId=" + restaurant1.getId())
                .with(userHttpBasic(admin))
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(newVoteTo)))
                .andDo(print())
                .andExpect(status().isUnprocessableEntity());
    }

    @Test
    void createRestaurantNotFound() throws Exception {
        VoteTo newVoteTo = new VoteTo();
        newVoteTo.setDateTime(LocalDateTime.of(LocalDate.now(), LocalTime.of(10, 59)));
        perform(MockMvcRequestBuilders.post(REST_URL + "?restaurantId=" + NOT_FOUND)
                .with(userHttpBasic(admin))
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(newVoteTo)))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    void getUnauth() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void postUnauth() throws Exception {
        VoteTo newVoteTo = new VoteTo();
        User unAuthUser = UserTestData.getNew();
        perform(MockMvcRequestBuilders.post(REST_URL)
                .with(userHttpBasic(unAuthUser))
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(newVoteTo)))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void getTodayAuthUserVote() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL + "/user-today-vote")
                .with(userHttpBasic(admin)))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(VOTE_TO_MATCHER.contentJson(service.convertVoteVoteTo(vote4)));
    }

    @Test
    void getAbsentTodayAuthUserVote() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL + "/user-today-vote")
                .with(userHttpBasic(user)))
                .andExpect(status().isNotFound());
    }

    @Test
    void getTodayNotAuthUserVote() throws Exception {
        User unAuthUser = UserTestData.getNew();
        perform(MockMvcRequestBuilders.get(REST_URL + "/user-today-vote")
                .with(userHttpBasic(unAuthUser)))
                .andExpect(status().isUnauthorized());
    }
}
