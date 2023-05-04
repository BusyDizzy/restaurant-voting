package com.antontkatch.restaurant.to;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;


@EqualsAndHashCode(callSuper = true)
@Getter
@Setter
@NoArgsConstructor
public class VoteTo extends BaseTo {

    @Schema(accessMode = Schema.AccessMode.READ_ONLY)
    @DateTimeFormat
    LocalDateTime dateTime = LocalDateTime.now();

    @Schema(accessMode = Schema.AccessMode.READ_ONLY)
    Integer restaurantId;
}
