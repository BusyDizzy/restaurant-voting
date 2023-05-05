package com.antontkatch.restaurant.to;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.time.LocalDateTime;


@EqualsAndHashCode(callSuper = true)
@Getter
@Setter
@NoArgsConstructor
@ToString(callSuper = true)
public class VoteTo extends BaseTo {

    @Schema(accessMode = Schema.AccessMode.READ_ONLY)
    LocalDateTime dateTime = LocalDateTime.now();

    @Schema(accessMode = Schema.AccessMode.READ_ONLY)
    Integer restaurantId;
}
