package com.antontkatch.restaurant.to;

import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDate;

@Value
@EqualsAndHashCode(callSuper = true)
@Getter
@Setter
@ToString(callSuper = true)
public class MenuTo extends BaseTo {

    @NotNull
    LocalDate date;

    public MenuTo(Integer id, LocalDate date) {
        super(id);
        this.date = date;
    }
}