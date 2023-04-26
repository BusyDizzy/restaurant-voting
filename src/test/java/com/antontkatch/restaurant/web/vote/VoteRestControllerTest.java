package com.antontkatch.restaurant.web.vote;

import com.antontkatch.restaurant.model.Vote;
import com.antontkatch.restaurant.service.VoteService;
import com.antontkatch.restaurant.to.VoteTo;
import com.antontkatch.restaurant.util.JsonUtil;
import com.antontkatch.restaurant.web.AbstractControllerTest;
import com.antontkatch.restaurant.web.restaurant.RestaurantController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.time.LocalDate;

import static com.antontkatch.restaurant.RestaurantTestData.restaurant1;
import static com.antontkatch.restaurant.TestUtil.userHttpBasic;
import static com.antontkatch.restaurant.UserTestData.admin;
import static com.antontkatch.restaurant.UserTestData.user;
import static com.antontkatch.restaurant.VoteTestData.VOTE_MATCHER;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class VoteRestControllerTest extends AbstractControllerTest {

    private static final String REST_URL = RestaurantController.REST_URL + '/';

    @Autowired
    private VoteService service;

    @Test
    void createVote() throws Exception {
        VoteTo newVoteTo = new VoteTo();

        ResultActions action = perform(MockMvcRequestBuilders.post(REST_URL + restaurant1.getId() + "/votes")
                .with(userHttpBasic(user))
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(newVoteTo)))
                .andDo(print())
                .andExpect(status().isCreated());

        Vote created = VOTE_MATCHER.readFromJson(action);
        Vote newVote = new Vote(null, LocalDate.now(), restaurant1, user);
        int newId = created.id();
        newVote.setId(newId);
        VOTE_MATCHER.assertMatch(created, newVote);
        VOTE_MATCHER.assertMatch(service.get(newId, user.id()), newVote);
    }

    @Test
    void voteUpdateBeforeTime() throws Exception {
        VoteTo newVoteTo = new VoteTo();

        ResultActions action = perform(MockMvcRequestBuilders.post(REST_URL + restaurant1.getId() + "/votes")
                .with(userHttpBasic(admin))
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(newVoteTo)))
                .andDo(print())
                .andExpect(status().isCreated());

        Vote created = VOTE_MATCHER.readFromJson(action);
        Vote newVote = new Vote(null, LocalDate.now(), restaurant1, admin);
        int newId = created.id();
        newVote.setId(newId);
        VOTE_MATCHER.assertMatch(created, newVote);
        VOTE_MATCHER.assertMatch(service.get(newId, admin.id()), newVote);
    }
}
