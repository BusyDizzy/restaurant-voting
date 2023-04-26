package com.antontkatch.restaurant.repository;

import com.antontkatch.restaurant.error.NotFoundException;
import com.antontkatch.restaurant.model.Vote;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Transactional(readOnly = true)
public interface VoteRepository extends BaseRepository<Vote> {

    @Transactional
    @Modifying
    @Query("DELETE FROM Vote v WHERE v.id=:id AND v.user.id=:userId AND v.restaurant.id=:restaurantId")
    int delete(@Param("id") int id, @Param("userId") int userId, @Param("restaurantId") int restaurantId);

    @EntityGraph(attributePaths = {"restaurant"}, type = EntityGraph.EntityGraphType.LOAD)
    @Query("SELECT v FROM Vote v WHERE v.date = CURRENT_DATE")
    List<Vote> getAllTodayVotes();

    default Vote getExisted(int id, int userId) {
        return findById(id)
                .filter(vote -> vote.getUser().getId() == userId)
                .orElseThrow(() -> new NotFoundException("Entity with id=" + id + " not found"));
    }

    @Query("SELECT v FROM Vote v WHERE v.date = CURRENT_DATE AND v.user.id=:userId")
    Optional<Vote> getTodayVoteIfExists(@Param("userId") int userId);
}
