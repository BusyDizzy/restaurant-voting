package com.antontkatch.restaurant.to;

import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@Value
@EqualsAndHashCode(callSuper = true)
@Getter
@Setter
@NoArgsConstructor
public class VoteTo extends BaseTo {

    @DateTimeFormat
    LocalDateTime localDateTime = LocalDateTime.now();
}
