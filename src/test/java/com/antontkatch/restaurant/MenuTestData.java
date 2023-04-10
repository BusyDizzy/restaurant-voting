package com.antontkatch.restaurant;

import com.antontkatch.restaurant.model.Menu;

import java.time.LocalDate;
import java.time.Month;
import java.util.List;

import static com.antontkatch.restaurant.DishTestData.*;
import static com.antontkatch.restaurant.RestaurantTestData.*;


public class MenuTestData {

    public static final MatcherFactory.Matcher<Menu> MENU_MATCHER = MatcherFactory.usingIgnoringFieldsComparator(Menu.class, "dishes", "restaurant");

    public static final MatcherFactory.Matcher<Menu> MENU_WITH_DISHES_MATCHER = MatcherFactory.usingEqualsComparator(Menu.class);

    public static final int NOT_FOUND = 10;

    public static final int MENU1_ID = 1;

    public static final int MENU2_ID = 2;

    public static final int MENU3_ID = 3;
    public static final int MENU4_ID = 4;

    public static final int MENU5_ID = 5;

    public static final int MENU6_ID = 6;

    public static final Menu menu1 = new Menu(MENU1_ID, LocalDate.of(2023, Month.MARCH, 6), restaurant1);

    public static final Menu menu2 = new Menu(MENU2_ID, LocalDate.of(2023, Month.MARCH, 6), restaurant2);

    public static final Menu menu3 = new Menu(MENU3_ID, LocalDate.of(2023, Month.MARCH, 6), restaurant3);

    public static final Menu menu4 = new Menu(MENU4_ID, LocalDate.of(2023, Month.APRIL, 10), restaurant1);

    public static final Menu menu5 = new Menu(MENU5_ID, LocalDate.of(2023, Month.APRIL, 10), restaurant2);

    public static final Menu menu6 = new Menu(MENU6_ID, LocalDate.of(2023, Month.APRIL, 10), restaurant3);

    public static Menu getNew() {
        return new Menu(null, LocalDate.of(2023, Month.APRIL, 7), restaurant1);
    }

    public static Menu getUpdated() {
        return new Menu(MENU1_ID, LocalDate.of(2023, Month.APRIL, 8), restaurant1);
    }

    static {
        menu4.setDishes(List.of(dish7, dish8, dish9));
    }
}
