package com.antontkatch.restaurant.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "menu", uniqueConstraints = {@UniqueConstraint(columnNames = {"date_added", "restaurant_id"}, name = "menu_unique_date_restaurant_idx")})
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true, exclude = {"dishes", "restaurant"})
public class Menu extends AbstractBaseEntity {

    @Column(name = "date_added", nullable = false)
    @NotNull
    private LocalDate date;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "menu")
    @OnDelete(action = OnDeleteAction.CASCADE)
    @Schema(accessMode = Schema.AccessMode.READ_ONLY)
    private List<Dish> dishes;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "restaurant_id", nullable = false)
    @NotNull
    @JsonIgnore
    @Schema(accessMode = Schema.AccessMode.READ_ONLY)
    private Restaurant restaurant;

    public Menu(Integer id, LocalDate date) {
        super(id);
        this.date = date;
    }

    public Menu(Integer id, LocalDate date, Restaurant restaurant) {
        super(id);
        this.date = date;
        this.restaurant = restaurant;
    }
}