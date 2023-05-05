package com.antontkatch.restaurant.to;

import com.antontkatch.restaurant.model.Menu;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

@Value
@EqualsAndHashCode(callSuper = true)
@Getter
@Setter
@ToString(callSuper = true)
public class RestaurantTo extends NamedTo {

    @NotBlank
    @Size(max = 128)
    String address;

    Menu menu;

    public RestaurantTo(Integer id, String name, String address, Menu menu) {
        super(id, name);
        this.address = address;
        this.menu = menu;
    }
}
