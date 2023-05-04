package com.antontkatch.restaurant.util;

import com.antontkatch.restaurant.to.VoteTo;
import com.antontkatch.restaurant.web.AuthUser;
import lombok.experimental.UtilityClass;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

@UtilityClass
public class VoteUtil {

    DateTimeFormatter formatter = DateTimeFormatter.ISO_TIME;
    public static final LocalTime VOTE_TIME_LIMIT = LocalTime.of(11, 0, 0);
    public static final String TIME_IS_EXCEEDED =
            String.format("Sorry. Changing your vote is possible only till %s AM. Please come back tomorrow",
                    VOTE_TIME_LIMIT.format(formatter));

    public static final String NOT_VOTED_TODAY = String.format("User with login %s has not yet voted today", AuthUser.authUser().getEmail());

    public static boolean validateVoteTime(VoteTo voteTo) {
        return voteTo.getDateTime().toLocalTime().isAfter(VOTE_TIME_LIMIT);
    }
}