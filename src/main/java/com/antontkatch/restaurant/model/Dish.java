package com.antontkatch.restaurant.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Range;


@Entity
@Table(name = "dish")
@Getter
@Setter
public class Dish extends AbstractNamedEntity {

    @Column(name = "price", nullable = false)
    @Range(min = 0, max = 10000)
    private double price;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "menu_id", nullable = false)
    @NotNull
    @JsonBackReference(value = "menu-dishes")
    private Menu menu;

    public Dish() {
    }

    public Dish(String dishName, double price) {
        this(null, dishName, price);
    }

    public Dish(Integer id, String name, double price) {
        super(id, name);
        this.price = price;
    }

    public Dish(Integer id, String name, double price, Menu menu) {
        super(id, name);
        this.price = price;
        this.menu = menu;
    }

    @Override
    public String toString() {
        return "Dish{" +
                "id=" + id +
                ", price=" + price +
                ", name='" + name + '\'' +
                '}';
    }
}
