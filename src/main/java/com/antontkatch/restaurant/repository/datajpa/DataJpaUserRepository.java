package com.antontkatch.restaurant.repository.datajpa;

import com.antontkatch.restaurant.model.User;
import com.antontkatch.restaurant.repository.UserRepository;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.List;

import static com.antontkatch.restaurant.util.validation.ValidationUtil.checkNotFound;
import static com.antontkatch.restaurant.util.validation.ValidationUtil.checkNotFoundWithId;

@Repository
public class DataJpaUserRepository implements UserRepository {

    private final CrudUserRepository crudUserRepository;

    public DataJpaUserRepository(CrudUserRepository crudUserRepository) {
        this.crudUserRepository = crudUserRepository;
    }

    @Override
    @Transactional
    @CacheEvict(value = "users", allEntries = true)
    public User save(User user) {
        Assert.notNull(user, "user must not be null");
        checkNotFoundWithId(crudUserRepository.save(user), user.id());
        return user;
    }

    @Override
    @CacheEvict(value = "users", allEntries = true)
    public boolean delete(int id) {
        checkNotFoundWithId(crudUserRepository.delete(id) != 0, id);
        return true;
    }

    @Override
    public User get(int id) {
        User user = crudUserRepository.findById(id).orElse(null);
        checkNotFoundWithId(user, id);
        return user;
    }

    @Override
    public User getByEmail(String email) {
        Assert.notNull(email, "email must not be null");
        User user = crudUserRepository.getByEmail(email);
        checkNotFound(user, "email=" + email);
        return user;
    }

    @Override
    @Cacheable("users")
    public List<User> getAll() {
        return crudUserRepository.findAll();
    }

    @CacheEvict(value = "users", allEntries = true)
    @Transactional
    public void enable(int id, boolean enabled) {
        User user = get(id);
        user.setEnabled(enabled);
    }
}
