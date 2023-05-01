package com.antontkatch.restaurant.service;

import com.antontkatch.restaurant.model.Vote;
import com.antontkatch.restaurant.repository.RestaurantRepository;
import com.antontkatch.restaurant.repository.UserRepository;
import com.antontkatch.restaurant.repository.VoteRepository;
import com.antontkatch.restaurant.to.VoteTo;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Service
public class VoteService {

    private final ModelMapper modelMapper;

    private final VoteRepository voteRepository;

    private final RestaurantRepository restaurantRepository;

    private final UserRepository userRepository;

    @Transactional
    public Vote save(Vote vote, int userId, int restaurantId) {
        if (!vote.isNew() && voteRepository.getExisted(vote.id(), userId) == null) {
            return null;
        }

        vote.setUser(userRepository.getReferenceById(userId));
        vote.setRestaurant(restaurantRepository.getExisted(restaurantId));
        return voteRepository.save(vote);
    }

    public List<Vote> getAllTodayVotes() {
        return voteRepository.getAllTodayVotes();
    }

    public Vote get(int id, int userId) {
        return voteRepository.getExisted(id, userId);
    }

    public Optional<Vote> getTodayVoteIfExists(int userId) {
        return voteRepository.getTodayVoteIfExists(userId);
    }

    public Vote convertVoteToVote(VoteTo voteTo) {
        return modelMapper.map(voteTo, Vote.class);
    }
}