package com.antontkatch.restaurant.web.user;

import com.antontkatch.restaurant.model.User;
import com.antontkatch.restaurant.repository.datajpa.DataJpaUserRepository;
import com.antontkatch.restaurant.to.UserTo;
import com.antontkatch.restaurant.util.UserUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;

import java.util.List;

import static com.antontkatch.restaurant.util.validation.ValidationUtil.assureIdConsistent;
import static com.antontkatch.restaurant.util.validation.ValidationUtil.checkNew;

@Slf4j
public abstract class AbstractUserController {

    @Autowired
    private DataJpaUserRepository repository;

    @Autowired
    private UniqueMailValidator emailValidator;

    @InitBinder
    protected void initBinder(WebDataBinder binder) {
        binder.addValidators(emailValidator);
    }

    @Cacheable("users")
    public List<User> getAll() {
        log.info("getAll");
        return repository.getAll();
    }

    public User get(int id) {
        log.info("get {}", id);
        return repository.get(id);
    }

//    public void create(UserTo userTo) {
//        log.info("create {}", userTo);
//        checkNew(userTo);
//        repository.save(UserUtil.createNewFromTo(userTo));
//    }

    @CacheEvict(value = "users", allEntries = true)
    public User create(User user) {
        log.info("create {}", user);
        checkNew(user);
        return repository.save(user);
    }

    public void delete(int id) {
        log.info("delete {}", id);
        repository.delete(id);
    }

    public void update(User user, int id) {
        log.info("update {} with id={}", user, id);
        assureIdConsistent(user, id);
        repository.save(user);
    }

    public void update(UserTo userTo, int id) {
        assureIdConsistent(userTo, id);
        log.info("update {} with id={}", userTo, id);
        User user = get(userTo.id());
        UserUtil.updateFromTo(user, userTo);
        repository.save(user);
    }

    public User getByMail(String email) {
        log.info("getByEmail {}", email);
        return repository.getByEmail(email);
    }

    public void enable(int id, boolean enabled) {
        log.info(enabled ? "enable {}" : "disable {}", id);
        repository.enable(id, enabled);
    }
}