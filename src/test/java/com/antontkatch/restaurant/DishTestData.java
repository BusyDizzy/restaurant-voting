package com.antontkatch.restaurant;

import com.antontkatch.restaurant.model.Dish;

import java.util.List;

import static com.antontkatch.restaurant.MenuTestData.*;

public class DishTestData {

    public static final MatcherFactory.Matcher<Dish> DISH_MATCHER = MatcherFactory.usingEqualsComparator(Dish.class);

    public static final int DISH1_ID = 1;

    public static final int DISH2_ID = 2;

    public static final int DISH3_ID = 3;

    public static final int DISH4_ID = 4;

    public static final int DISH5_ID = 5;

    public static final int DISH6_ID = 6;

    public static final int DISH7_ID = 7;

    public static final int DISH8_ID = 8;

    public static final int DISH9_ID = 9;

    public static final int DISH10_ID = 10;

    public static final int DISH11_ID = 11;

    public static final int DISH12_ID = 12;


    public static final Dish dish1 = new Dish(DISH1_ID, "Pepperoni Pizza", 10, menu1);
    public static final Dish dish2 = new Dish(DISH2_ID, "Coca Cola", 3, menu1);
    public static final Dish dish3 = new Dish(DISH3_ID, "Cheeseburger", 9, menu1);
    public static final Dish dish4 = new Dish(DISH4_ID, "Bacon Cheeseburger", 10, menu2);
    public static final Dish dish5 = new Dish(DISH5_ID, "California Roll", 12, menu2);
    public static final Dish dish6 = new Dish(DISH6_ID, "Spicy Tuna Roll", 13, menu3);

    public static final Dish dish7 = new Dish(DISH7_ID, "Margarita Pizza", 10, menu4);
    public static final Dish dish8 = new Dish(DISH8_ID, "Pepsi Cola", 3, menu4);
    public static final Dish dish9 = new Dish(DISH9_ID, "Green Salad", 5, menu4);
    public static final Dish dish10 = new Dish(DISH10_ID, "Beyond Meat Cheeseburger", 16, menu5);
    public static final Dish dish11 = new Dish(DISH11_ID, "Crispy Fried Roll", 15, menu5);
    public static final Dish dish12 = new Dish(DISH12_ID, "Spicy Tuna Roll", 13, menu6);

    public static Dish getNew() {
        return new Dish(null, "Anti Pasta", 12);
    }

    public static Dish getUpdated() {
        return new Dish(DISH1_ID, "Pepperoni Pizza Updated", 13);
    }

    static {
        menu4.setDishes(List.of(dish7, dish8, dish9));
    }
}
