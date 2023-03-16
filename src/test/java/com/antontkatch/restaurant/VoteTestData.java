package com.antontkatch.restaurant;

import com.antontkatch.restaurant.model.Vote;

import java.time.LocalDate;
import java.time.Month;

import static com.antontkatch.restaurant.RestaurantTestData.restaurant1;
import static com.antontkatch.restaurant.RestaurantTestData.restaurant2;
import static com.antontkatch.restaurant.UserTestData.*;


public class VoteTestData {

    public static final MatcherFactory.Matcher<Vote> VOTE_MATCHER = MatcherFactory.usingIgnoringFieldsComparator();

    public static final int NOT_FOUND = 10;

    public static final int VOTE1_ID = 1;

    public static final int VOTE2_ID = 2;

    public static final int VOTE3_ID = 3;

    public static final int VOTE4_ID = 4;

    public static final int VOTE5_ID = 5;

    public static final int VOTE6_ID = 6;

    public static final Vote vote1 = new Vote(VOTE1_ID, LocalDate.of(2023, Month.MARCH, 6), restaurant1, user);
    public static final Vote vote2 = new Vote(VOTE2_ID, LocalDate.of(2023, Month.MARCH, 6), restaurant2, admin);
    public static final Vote vote3 = new Vote(VOTE3_ID, LocalDate.of(2023, Month.MARCH, 6), restaurant1, guest);

    public static final Vote vote4 = new Vote(VOTE4_ID, LocalDate.of(2023, Month.MARCH, 11), restaurant2, user);
    public static final Vote vote5 = new Vote(VOTE5_ID, LocalDate.of(2023, Month.MARCH, 11), restaurant2, admin);
    public static final Vote vote6 = new Vote(VOTE6_ID, LocalDate.of(2023, Month.MARCH, 11), restaurant1, guest);

}
