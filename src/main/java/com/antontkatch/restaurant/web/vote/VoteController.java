package com.antontkatch.restaurant.web.vote;

import com.antontkatch.restaurant.model.Vote;
import com.antontkatch.restaurant.service.VoteService;
import com.antontkatch.restaurant.to.VoteTo;
import com.antontkatch.restaurant.web.AuthUser;
import com.antontkatch.restaurant.web.restaurant.RestaurantController;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import static com.antontkatch.restaurant.util.VoteUtil.validateVoteTime;

@RestController
@RequestMapping(value = VoteController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
@Slf4j
public class VoteController {

    public static final String REST_URL = RestaurantController.REST_URL + "/";

    @Autowired
    private VoteService service;

    @GetMapping("votes")
    public List<Vote> getAllTodayVotes() {
        return service.getAllTodayVotes();
    }

    @GetMapping("votes/{voteId}")
    public Vote get(@PathVariable int voteId, @AuthenticationPrincipal AuthUser authUser) {
        int userId = authUser.getUser().id();
        log.info("get vote {} of user id{}", voteId, userId);
        return service.get(voteId, userId);
    }

    @PostMapping(value = "{restaurantId}/votes", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Vote> createOrUpdate(@Valid @RequestBody VoteTo voteTo, @PathVariable int restaurantId, @AuthenticationPrincipal AuthUser authUser) {
        int userId = authUser.getUser().id();
        Optional<Vote> voteDb = service.getTodayVoteIfExists(userId);
        Vote newVote;
        if (voteDb.isPresent() && validateVoteTime(voteTo)) {
            return ResponseEntity.ok(voteDb.get());
        } else newVote = voteDb.orElseGet(() -> service.convertVoteToVote(voteTo));
        String URL = RestaurantController.REST_URL + "/" + restaurantId + "/votes";
        log.info("create {}", newVote);
        Vote created = service.save(newVote, userId, restaurantId);
        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(URL + "/{id}")
                .buildAndExpand(newVote.getId()).toUri();
        return ResponseEntity.created(uriOfNewResource).body(created);
    }
}
