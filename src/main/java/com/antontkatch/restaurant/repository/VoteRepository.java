package com.antontkatch.restaurant.repository;

import com.antontkatch.restaurant.model.Vote;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional(readOnly = true)
public interface VoteRepository extends BaseRepository<Vote> {

    @Query(value = "SELECT * FROM vote WHERE vote_date = CURRENT_DATE", nativeQuery = true)
    List<Vote> getAllTodaysVotes();
}
