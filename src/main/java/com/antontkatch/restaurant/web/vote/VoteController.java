package com.antontkatch.restaurant.web.vote;

import com.antontkatch.restaurant.model.Vote;
import com.antontkatch.restaurant.repository.VoteRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = VoteController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
@Slf4j
public class VoteController {

    static final String REST_URL = "/api/votes";

    @Autowired
    private VoteRepository repository;

    @GetMapping
    public List<Vote> getAllTodayVotes() {
        return repository.getAllTodaysVotes();
    }
}
