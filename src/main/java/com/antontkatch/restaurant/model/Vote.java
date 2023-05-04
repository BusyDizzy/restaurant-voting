package com.antontkatch.restaurant.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "vote", uniqueConstraints = {@UniqueConstraint(columnNames = {"user_id", "restaurant_id", "vote_date"}, name = "vote_unique_user_date_restaurant_idx")})
@Getter
@Setter
@RequiredArgsConstructor
@AllArgsConstructor
public class Vote extends AbstractBaseEntity {

    @Column(name = "vote_date", columnDefinition = "timestamp default now()", nullable = false)
    @NotNull
    private LocalDateTime dateTime;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "restaurant_id", nullable = false)
    @NotNull
//    @JsonBackReference(value = "restaurant-votes")
    @Schema(accessMode = Schema.AccessMode.READ_ONLY)
    private Restaurant restaurant;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    @NotNull
//    @JsonBackReference(value = "user-votes")
    @JsonIgnore
    @Schema(accessMode = Schema.AccessMode.READ_ONLY)
    private User user;

    public Vote(Integer id, LocalDateTime dateTime, Restaurant restaurant, User user) {
        super(id);
        this.dateTime = dateTime;
        this.restaurant = restaurant;
        this.user = user;
    }

    @Override
    public String toString() {
        return "Vote{" +
                "id=" + id +
                ", date=" + dateTime +
                '}';
    }
}
