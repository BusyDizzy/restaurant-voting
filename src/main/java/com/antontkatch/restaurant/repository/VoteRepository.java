package com.antontkatch.restaurant.repository;

import com.antontkatch.restaurant.model.Vote;

import java.time.LocalDate;
import java.util.List;

public interface VoteRepository {

    Vote get(int id, int userId, int restaurantId);

    boolean delete(int id, int userId, int restaurantId);

    Vote save(Vote vote, int userId, int restaurantId);

    List<Vote> getAllUserVotes(int userId);

    List<Vote> getAllRestaurantVotes(int restaurantId);

    List<Vote> getAll(int restaurantId, LocalDate date);

    List<Vote> getAllDailyVotes(LocalDate date);
}
