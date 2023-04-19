package com.antontkatch.restaurant.repository.datajpa;

import com.antontkatch.restaurant.model.User;
import com.antontkatch.restaurant.util.UserUtil;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.List;
import java.util.Optional;

import static com.antontkatch.restaurant.util.validation.ValidationUtil.checkNotFound;
import static com.antontkatch.restaurant.util.validation.ValidationUtil.checkNotFoundWithId;

@Repository
@Transactional(readOnly = true)
public class DataJpaUserRepository {

    private final CrudUserRepository crudUserRepository;

    public DataJpaUserRepository(CrudUserRepository crudUserRepository) {
        this.crudUserRepository = crudUserRepository;
    }

    @Transactional
    @CacheEvict(value = "users", allEntries = true)
    public User save(User user) {
        Assert.notNull(user, "user must not be null");
        checkNotFoundWithId(crudUserRepository.save(user), user.id());
        return prepareAndSave(user);
    }

    @CacheEvict(value = "users", allEntries = true)
    public boolean delete(int id) {
        checkNotFoundWithId(crudUserRepository.delete(id) != 0, id);
        return true;
    }

    public User get(int id) {
        User user = crudUserRepository.findById(id).orElse(null);
        checkNotFoundWithId(user, id);
        return user;
    }

    public User getByEmail(String email) {
        Assert.notNull(email, "email must not be null");
        Optional<User> user = crudUserRepository.findByEmailIgnoreCase(email);
        checkNotFound(user, "email=" + email);
        return user.orElse(null);
    }

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

    public Optional<User> findByEmailIgnoreCase(String email) {
        return crudUserRepository.findByEmailIgnoreCase(email);
    }

    protected User prepareAndSave(User user) {
        return crudUserRepository.save(UserUtil.prepareToSave(user));
    }
}
