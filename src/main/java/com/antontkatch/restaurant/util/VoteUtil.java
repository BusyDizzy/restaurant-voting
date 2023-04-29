package com.antontkatch.restaurant.util;

import com.antontkatch.restaurant.to.VoteTo;
import lombok.experimental.UtilityClass;

import java.time.LocalTime;

@UtilityClass
public class VoteUtil {
    private static final LocalTime VOTE_TIME_LIMIT = LocalTime.of(11, 0);

    public static boolean validateVoteTime(VoteTo voteTo) {
        return voteTo.getTime().isAfter(VOTE_TIME_LIMIT);
    }
}