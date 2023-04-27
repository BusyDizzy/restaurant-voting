package com.antontkatch.restaurant.to;

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

    @DateTimeFormat
    LocalDateTime localDateTime;

    public VoteTo(Integer id, LocalDateTime localDateTime) {
        super(id);
        this.localDateTime = localDateTime;
    }
}
