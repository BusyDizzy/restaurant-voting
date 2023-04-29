package com.antontkatch.restaurant.to;

import jakarta.validation.constraints.NotNull;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.Value;

import java.time.LocalDate;

@Value
@EqualsAndHashCode(callSuper = true)
@Getter
@Setter
public class MenuTo extends BaseTo {

    @NotNull
    LocalDate date;

    public MenuTo(Integer id, LocalDate date) {
        super(id);
        this.date = date;
    }
}