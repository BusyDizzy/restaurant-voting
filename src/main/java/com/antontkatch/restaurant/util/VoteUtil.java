package com.antontkatch.restaurant.util;

import com.antontkatch.restaurant.model.Vote;
import com.antontkatch.restaurant.to.VoteTo;
import lombok.experimental.UtilityClass;

import java.time.LocalTime;

@UtilityClass
public class VoteUtil {

    private static final LocalTime VOTE_TIME_LIMIT = LocalTime.of(11, 0);

    public static Vote createNewFromTo(VoteTo voteTo) {
        return new Vote(null, voteTo.getLocalDateTime().toLocalDate());
    }

    public static boolean checkTime(VoteTo voteTo) {
        return voteTo.getLocalDateTime().toLocalTime().compareTo(VOTE_TIME_LIMIT) >= 0;
    }
}