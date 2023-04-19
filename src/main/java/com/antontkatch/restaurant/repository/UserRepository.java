//package com.antontkatch.restaurant.repository;
//
//import com.antontkatch.restaurant.model.User;
//
//import java.util.List;
//import java.util.Optional;
//
//public interface UserRepository {
//    // null if not found, when updated
//    User save(User user);
//
//    // false if not found
//    boolean delete(int id);
//
//    // null if not found
//    User get(int id);
//
//    // null if not found
//    User getByEmail(String email);
//
//    List<User> getAll();
//
//    void enable(int id, boolean enabled);
//
//    Optional<User> findByEmailIgnoreCase(String email);
//}
