package com.devsync.repository.interfaces;

import com.devsync.entity.User;

import java.util.List;
import java.util.Optional;

public interface UserRepository {
    List<User> findAll();

    void create(User user);

    void update(User user);

    void delete(Long userId);

    Optional<User> findById(Long userId); // New method

    Optional<User> findByUsernameAndPassword(String username, String password);
}
