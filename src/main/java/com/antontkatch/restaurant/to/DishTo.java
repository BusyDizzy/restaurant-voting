package com.antontkatch.restaurant.to;

import jakarta.validation.constraints.NotNull;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.Value;
import org.hibernate.validator.constraints.Range;

@Value
@EqualsAndHashCode(callSuper = true)
@Getter
@Setter
public class DishTo extends NamedTo {

    @NotNull
    @Range(min = 1, max = 10000)
    Double price;

    public DishTo(Integer id, String name, Double price) {
        super(id, name);
        this.price = price;
    }
}