package com.antontkatch.restaurant.repository.datajpa;

import com.antontkatch.restaurant.model.Vote;
import com.antontkatch.restaurant.repository.VoteRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Repository
public class DataJpaVoteRepository implements VoteRepository {

    private final CrudVoteRepository crudVoteRepository;

    private final CrudRestaurantRepository crudRestaurantRepository;

    private final CrudUserRepository crudUserRepository;

    public DataJpaVoteRepository(CrudVoteRepository crudVoteRepository,
                                 CrudRestaurantRepository crudRestaurantRepository,
                                 CrudUserRepository crudUserRepository) {
        this.crudVoteRepository = crudVoteRepository;
        this.crudRestaurantRepository = crudRestaurantRepository;
        this.crudUserRepository = crudUserRepository;
    }

    @Override
    public Vote get(int id, int userId, int restaurantId) {
        return crudVoteRepository.findById(id)
                .filter(vote -> vote.getUser().getId() == userId)
                .filter(vote -> vote.getRestaurant().getId() == restaurantId)
                .orElse(null);
    }

    @Override
    public boolean delete(int id, int userId, int restaurantId) {
        return crudVoteRepository.delete(id, userId, restaurantId) != 0;
    }

    @Override
    @Transactional
    public Vote save(Vote vote, int userId, int restaurantId) {
        if (!vote.isNew() && get(vote.id(), userId, restaurantId) == null) {
            return null;
        }
        vote.setUser(crudUserRepository.getReferenceById(userId));
        vote.setRestaurant(crudRestaurantRepository.getReferenceById(restaurantId));
        return crudVoteRepository.save(vote);
    }

    @Override
    public List<Vote> getAllUserVotes(int userId) {
        return crudVoteRepository.getAllUserVotes(userId);
    }

    @Override
    public List<Vote> getAllRestaurantVotes(int restaurantId) {
        return crudVoteRepository.getAllRestaurantVotes(restaurantId);
    }

    @Override
    public List<Vote> getAll(int restaurantId, LocalDate date) {
        return crudVoteRepository.getAll(restaurantId, date);
    }

    @Override
    public List<Vote> getAllDailyVotes(LocalDate date) {
        return crudVoteRepository.getAllDailyVotes(date);
    }
}
