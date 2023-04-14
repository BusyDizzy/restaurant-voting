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


    public static final Dish dish1 = new Dish(DISH1_ID, "Pepperoni Pizza", 10.99, menu1);
    public static final Dish dish2 = new Dish(DISH2_ID, "Coca Cola", 2.99, menu1);
    public static final Dish dish3 = new Dish(DISH3_ID, "Cheeseburger", 8.99, menu1);
    public static final Dish dish4 = new Dish(DISH4_ID, "Bacon Cheeseburger", 9.99, menu2);
    public static final Dish dish5 = new Dish(DISH5_ID, "California Roll", 11.99, menu2);
    public static final Dish dish6 = new Dish(DISH6_ID, "Spicy Tuna Roll", 12.99, menu3);

    public static final Dish dish7 = new Dish(DISH7_ID, "Margarita Pizza", 9.99, menu4);
    public static final Dish dish8 = new Dish(DISH8_ID, "Pepsi Cola", 2.98, menu4);
    public static final Dish dish9 = new Dish(DISH9_ID, "Green Salad", 4.99, menu4);
    public static final Dish dish10 = new Dish(DISH10_ID, "Beyond Meat Cheeseburger", 15.99, menu5);
    public static final Dish dish11 = new Dish(DISH11_ID, "Crispy Fried Roll", 14.99, menu5);
    public static final Dish dish12 = new Dish(DISH12_ID, "Spicy Tuna Roll", 12.99, menu6);

    public static Dish getNew() {
        return new Dish(null, "Anti Pasta", 12.33);
    }

    public static Dish getUpdated() {
        return new Dish(DISH1_ID, "Pepperoni Pizza Updated", 13.00);
    }

    static {
        menu4.setDishes(List.of(dish7, dish8, dish9));
    }
}
