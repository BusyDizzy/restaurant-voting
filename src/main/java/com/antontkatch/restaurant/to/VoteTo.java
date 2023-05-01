package com.antontkatch.restaurant.to;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;


@EqualsAndHashCode(callSuper = true)
@Getter
@Setter
@NoArgsConstructor
public class VoteTo extends BaseTo {

    @Schema(accessMode = Schema.AccessMode.READ_ONLY)
    private LocalDate date = LocalDate.now();

    @Schema(accessMode = Schema.AccessMode.READ_ONLY)
    private LocalTime time = LocalTime.now();
}
