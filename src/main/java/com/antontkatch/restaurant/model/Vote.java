package com.antontkatch.restaurant.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "vote", uniqueConstraints = {@UniqueConstraint(columnNames = {"user_id", "restaurant_id", "vote_date"}, name = "vote_unique_user_date_restaurant_idx")})
@Getter
@Setter
@RequiredArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true, exclude = {"restaurant", "user"})
public class Vote extends AbstractBaseEntity {

    @Column(name = "vote_date", columnDefinition = "timestamp default now()", nullable = false)
    @NotNull
    private LocalDateTime dateTime;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "restaurant_id", nullable = false)
    @NotNull
    @Schema(accessMode = Schema.AccessMode.READ_ONLY)
    private Restaurant restaurant;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    @NotNull
    @JsonIgnore
    @Schema(accessMode = Schema.AccessMode.READ_ONLY)
    private User user;

    public Vote(Integer id, LocalDateTime dateTime, Restaurant restaurant, User user) {
        super(id);
        this.dateTime = dateTime;
        this.restaurant = restaurant;
        this.user = user;
    }
}
