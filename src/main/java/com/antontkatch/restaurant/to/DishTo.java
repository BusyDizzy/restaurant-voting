package com.antontkatch.restaurant.to;

import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.validator.constraints.Range;

@Value
@EqualsAndHashCode(callSuper = true)
@Getter
@Setter
@ToString(callSuper = true)
public class DishTo extends NamedTo {

    @NotNull
    @Range(min = 1, max = 10000)
    Integer price;

    public DishTo(Integer id, String name, Integer price) {
        super(id, name);
        this.price = price;
    }
}