package com.antontkatch.restaurant.to;

import jakarta.validation.constraints.NotNull;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;


@EqualsAndHashCode(callSuper = true)
@Getter
@Setter
@NoArgsConstructor
public class VoteTo extends BaseTo {

    @NotNull
    private LocalDate date;

    @NotNull
    private LocalTime time;

    public VoteTo(Integer id, LocalDateTime localDateTime) {
        super(id);
        this.date = localDateTime.toLocalDate();
        this.time = localDateTime.toLocalTime();
    }
}
