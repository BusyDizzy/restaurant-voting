package com.antontkatch.restaurant.util;

import com.antontkatch.restaurant.model.Role;
import com.antontkatch.restaurant.model.User;
import com.antontkatch.restaurant.to.UserTo;
import lombok.experimental.UtilityClass;

import static com.antontkatch.restaurant.config.WebSecurityConfig.PASSWORD_ENCODER;


@UtilityClass
public class UserUtil {

    public static User createNewFromTo(UserTo userTo) {
        return new User(null, userTo.getName(), userTo.getEmail().toLowerCase(), userTo.getPassword(), Role.USER);
    }

    public static User updateFromTo(User user, UserTo userTo) {
        user.setName(userTo.getName());
        user.setEmail(userTo.getEmail().toLowerCase());
        user.setPassword(userTo.getPassword());
        return user;
    }

    public static UserTo asTo(User user) {
        return new UserTo(user.getId(), user.getName(), user.getEmail(), user.getPassword());
    }

    public static User prepareToSave(User user) {
        user.setPassword(PASSWORD_ENCODER.encode(user.getPassword()));
        user.setEmail(user.getEmail().toLowerCase());
        return user;
    }
}