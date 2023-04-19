package com.antontkatch.restaurant.web;


import com.antontkatch.restaurant.model.User;
import com.antontkatch.restaurant.to.UserTo;
import com.antontkatch.restaurant.util.UserUtil;
import lombok.Getter;
import lombok.ToString;
import org.springframework.lang.NonNull;

import java.io.Serial;

@Getter
@ToString(of = "userTo")
public class AuthorizedUser extends org.springframework.security.core.userdetails.User {
    @Serial
    private static final long serialVersionUID = 1L;

    private UserTo userTo;

    public AuthorizedUser(@NonNull User user) {
        super(user.getEmail(), user.getPassword(), user.isEnabled(), true, true, true, user.getRoles());
        this.userTo = UserUtil.asTo(user);
    }

    public int getId() {
        return userTo.id();
    }

    public void update(UserTo newTo) {
        userTo = newTo;
    }
}