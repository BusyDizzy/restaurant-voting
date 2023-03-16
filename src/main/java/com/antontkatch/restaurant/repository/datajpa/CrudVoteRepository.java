package com.antontkatch.restaurant.repository.datajpa;

import com.antontkatch.restaurant.model.Vote;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Transactional(readOnly = true)
public interface CrudVoteRepository extends JpaRepository<Vote, Integer> {
    @Transactional
    @Modifying
    @Query("DELETE FROM Vote v WHERE v.id=:id AND v.user.id=:userId AND v.restaurant.id=:restaurantId")
    int delete(@Param("id") int id, @Param("userId") int userId, @Param("restaurantId") int restaurantId);

    @Query("SELECT v FROM Vote v LEFT JOIN FETCH v.user LEFT JOIN FETCH v.restaurant WHERE v.date = ?1 ORDER BY v.restaurant.name")
    List<Vote> getAllDailyVotes(LocalDate date);

    @Query("SELECT v FROM Vote v LEFT JOIN FETCH v.user LEFT JOIN FETCH v.restaurant WHERE v.user.id = ?1 ORDER BY v.date DESC")
    List<Vote> getAllUserVotes(int userId);

    @Query("SELECT v FROM Vote v LEFT JOIN FETCH v.user LEFT JOIN FETCH v.restaurant WHERE v.restaurant.id = ?1 ORDER BY v.date DESC")
    List<Vote> getAllRestaurantVotes(int restaurantId);

    @Query("SELECT v FROM Vote v LEFT JOIN FETCH v.user LEFT JOIN FETCH v.restaurant WHERE v.restaurant.id = ?1 AND v.date = ?2 ORDER BY v.date DESC")
    List<Vote> getAll(int restaurantId, LocalDate date);
}
