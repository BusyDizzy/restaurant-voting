package com.antontkatch.restaurant.service;

import com.antontkatch.restaurant.model.Vote;
import com.antontkatch.restaurant.repository.VoteRepository;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.time.LocalDate;
import java.util.List;

import static com.antontkatch.restaurant.util.ValidationUtil.checkNotFoundWithId;

@Service
public class VoteService {

    private final VoteRepository repository;

    public VoteService(VoteRepository repository) {
        this.repository = repository;
    }

    public Vote get(int id, int userId, int restaurantId) {
        return checkNotFoundWithId(repository.get(id, userId, restaurantId), id);
    }

    public void delete(int id, int userId, int restaurantId) {
        checkNotFoundWithId(repository.delete(id, userId, restaurantId), id);
    }

    public List<Vote> getAllRestaurantVotes(int restaurantId) {
        return repository.getAllRestaurantVotes(restaurantId);
    }

    public List<Vote> getAllUserVotes(int userId) {
        return repository.getAllUserVotes(userId);
    }

    public List<Vote> getAll(int restaurantId, LocalDate date) {
        return repository.getAll(restaurantId, date);
    }

    public List<Vote> getAllDailyVotes(LocalDate date) {
        return repository.getAllDailyVotes(date);
    }

    public void update(Vote vote, int userId, int restaurantId) {
        Assert.notNull(vote, "vote must not be null");
        checkNotFoundWithId(repository.save(vote, userId, restaurantId), vote.id());
    }

    public Vote create(Vote vote, int userId, int restaurantId) {
        Assert.notNull(vote, "vote must not be null");
        return repository.save(vote, userId, restaurantId);
    }
}
