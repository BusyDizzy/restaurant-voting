package com.antontkatch.restaurant.web.user;

import com.antontkatch.restaurant.model.User;
import org.springframework.stereotype.Controller;

import static com.antontkatch.restaurant.web.SecurityUtil.authUserId;


@Controller
public class ProfileRestController extends AbstractUserController {

    public User get() {
        return super.get(authUserId());
    }

    public void delete() {
        super.delete(authUserId());
    }

    public void update(User user) {
        super.update(user, authUserId());
    }
}