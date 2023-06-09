package com.antontkatch.restaurant;

import com.antontkatch.restaurant.model.Vote;
import com.antontkatch.restaurant.to.VoteTo;

import java.time.LocalDateTime;
import java.time.Month;

import static com.antontkatch.restaurant.RestaurantTestData.*;
import static com.antontkatch.restaurant.UserTestData.*;


public class VoteTestData {

    public static final MatcherFactory.Matcher<Vote> VOTE_MATCHER = MatcherFactory.usingIgnoringFieldsComparator(Vote.class, "restaurant", "user", "dateTime");

    public static final MatcherFactory.Matcher<VoteTo> VOTE_TO_MATCHER = MatcherFactory.usingIgnoringFieldsComparator(VoteTo.class, "dateTime");

    public static final int NOT_FOUND = 100;

    public static final int VOTE1_ID = 1;

    public static final int VOTE2_ID = 2;

    public static final int VOTE3_ID = 3;

    public static final int VOTE4_ID = 4;

    public static final int VOTE5_ID = 5;

    public static final Vote vote1 = new Vote(VOTE1_ID, LocalDateTime.of(2023, Month.MARCH, 6, 10, 30, 0), restaurant1, user);
    public static final Vote vote2 = new Vote(VOTE2_ID, LocalDateTime.of(2023, Month.MARCH, 6, 10, 30, 0), restaurant2, admin);
    public static final Vote vote3 = new Vote(VOTE3_ID, LocalDateTime.of(2023, Month.MARCH, 6, 10, 30, 0), restaurant1, guest);

    public static final Vote vote4 = new Vote(VOTE4_ID, LocalDateTime.now(), restaurant3, admin);
    public static final Vote vote5 = new Vote(VOTE5_ID, LocalDateTime.now(), restaurant2, guest);

    public static Vote getNewAdminVoteNow() {
        return new Vote(null, LocalDateTime.now(), restaurant1, admin);
    }
}
