package com.antontkatch.restaurant.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.validator.constraints.Range;


@Entity
@Table(name = "dish", uniqueConstraints = {@UniqueConstraint(columnNames = {"menu_id", "name", "price"}, name = "dish_unique_menu_id_name_price_idx")})
@Getter
@Setter
@NoArgsConstructor
@ToString(callSuper = true, exclude = {"menu"})
public class Dish extends AbstractNamedEntity {

    @Column(name = "price", nullable = false)
    @Range(min = 1, max = 10000)
    @NotNull
    private Integer price;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "menu_id", nullable = false)
    @NotNull
    @Schema(accessMode = Schema.AccessMode.READ_ONLY)
    @JsonIgnore
    private Menu menu;

    public Dish(Integer id, String name, Integer price) {
        super(id, name);
        this.price = price;
    }

    public Dish(Integer id, String name, Integer price, Menu menu) {
        super(id, name);
        this.price = price;
        this.menu = menu;
    }
}
