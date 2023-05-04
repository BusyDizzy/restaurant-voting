package com.antontkatch.restaurant.web.vote;

import com.antontkatch.restaurant.error.NotFoundException;
import com.antontkatch.restaurant.model.Vote;
import com.antontkatch.restaurant.service.VoteService;
import com.antontkatch.restaurant.to.VoteTo;
import com.antontkatch.restaurant.web.AuthUser;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import static com.antontkatch.restaurant.util.VoteUtil.*;

@Tag(name = "Vote Controller")
@RestController
@RequestMapping(value = VoteController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
@Slf4j
public class VoteController {

    public static final String REST_URL = "/api/users/votes";

    @Autowired
    private VoteService service;

    @GetMapping()
    public List<VoteTo> getAllTodayVotes() {
        return service.getAllTodayVotes();
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    @CacheEvict(value = "votes", allEntries = true)
    public ResponseEntity<VoteTo> createOrUpdate(@RequestBody VoteTo voteTo, @RequestParam int restaurantId, @AuthenticationPrincipal AuthUser authUser) {
        int userId = authUser.getUser().id();
        Optional<Vote> voteDb = service.getTodayVoteIfExists(userId);
        Vote newVote;
        if (voteDb.isPresent() && validateVoteTime(voteTo)) {
            throw new IllegalArgumentException(TIME_IS_EXCEEDED);
        } else newVote = voteDb.orElseGet(() -> service.convertVoteToVote(voteTo));
        log.info("create {}", newVote);
        VoteTo created = service.save(newVote, userId, restaurantId);
        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(REST_URL + "/{id}")
                .buildAndExpand(newVote.getId()).toUri();
        return ResponseEntity.created(uriOfNewResource).body(created);
    }

    @GetMapping("/user-today-vote")
    public VoteTo get(@AuthenticationPrincipal AuthUser authUser) {
        Optional<Vote> voteDb = service.getTodayVoteIfExists(authUser.getUser().id());
        return voteDb.map(vote -> service.convertVoteVoteTo(vote)).orElseThrow(() -> new NotFoundException(NOT_VOTED_TODAY));
    }
}
