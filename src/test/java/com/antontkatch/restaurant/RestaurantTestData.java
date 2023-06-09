package com.antontkatch.restaurant;

import com.antontkatch.restaurant.model.Restaurant;
import com.antontkatch.restaurant.to.RestaurantTo;

import java.util.List;


public class RestaurantTestData {

    public static final MatcherFactory.Matcher<Restaurant> RESTAURANT_MATCHER = MatcherFactory.usingIgnoringFieldsComparator(Restaurant.class, "votes", "menus");

    public static final MatcherFactory.Matcher<RestaurantTo> RESTAURANT_TO_MATCHER = MatcherFactory.usingIgnoringFieldsComparator(RestaurantTo.class, "menu");

    public static final int NOT_FOUND = 100;
    public static final int RESTAURANT1_ID = 1;

    public static final int RESTAURANT2_ID = 2;

    public static final int RESTAURANT3_ID = 3;

    public static final int RESTAURANT4_ID = 4;

    public static final Restaurant restaurant1 = new Restaurant(RESTAURANT1_ID, "Pizza Palace", "123 Main St");

    public static final RestaurantTo restaurantTo1 = new RestaurantTo(restaurant1.getId(), restaurant1.getName(), restaurant1.getAddress(), null);

    public static final Restaurant restaurant2 = new Restaurant(RESTAURANT2_ID, "Burger Joint", "456 Oak St");

    public static final RestaurantTo restaurantTo2 = new RestaurantTo(restaurant2.getId(), restaurant2.getName(), restaurant2.getAddress(), null);

    public static final Restaurant restaurant3 = new Restaurant(RESTAURANT3_ID, "Sushi Spot", "789 Maple Ave");

    public static final RestaurantTo restaurantTo3 = new RestaurantTo(restaurant3.getId(), restaurant3.getName(), restaurant3.getAddress(), null);

    public static final Restaurant restaurant4 = new Restaurant(RESTAURANT4_ID, "Thai Local Food", "Moo 39/30");

    public static final List<Restaurant> restaurants = List.of(restaurant1, restaurant2, restaurant3);

    public static Restaurant getNew() {
        return new Restaurant(null, "Pizza Hut", "1st Ave, New-York");
    }

    public static Restaurant getUpdated() {
        return new Restaurant(RESTAURANT1_ID, "Pizza Castle", "987 Backup Rd");
    }
}

