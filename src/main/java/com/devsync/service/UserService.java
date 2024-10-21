package com.devsync.service;

import com.devsync.entity.User;
import com.devsync.repository.implementations.UserRepositoryImpl;
import com.devsync.repository.interfaces.UserRepository;

import java.util.List;
import java.util.Optional;

public class UserService {
    private final UserRepository userRepository;

    public UserService() {
        this.userRepository = new UserRepositoryImpl();
    }

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public Optional<User> findById(Long id) {
        return userRepository.findById(id);
    }

    public void create(User user) {
        if (user.getUsername() == null || user.getUsername().isEmpty()) {
            throw new RuntimeException("Username cannot be empty");
        }
        userRepository.create(user);
    }

    public void update(User user) {
        userRepository.update(user);
    }

    public void delete(Long id) {
        Optional<User> user = userRepository.findById(id);
        if (user.isPresent()) {
            userRepository.delete(id);
        } else {
            throw new RuntimeException("User not found for ID: " + id);
        }
    }

    public Optional<User> findByUsernameAndPassword(String username, String password) {
        return userRepository.findByUsernameAndPassword(username, password);
    }


    // Méthode pour réinitialiser les jetons mensuels
    public void resetMonthlyTokens() {
        List<User> users = userRepository.findAll();
        for (User user : users) {
            // Vérifie si les jetons mensuels sont inférieurs à 1
            if (user.getMonthlyTokens() < 1) {
                user.setMonthlyTokens(1); // Réinitialise à 1
            }

            userRepository.update(user);
        }
    }

}
