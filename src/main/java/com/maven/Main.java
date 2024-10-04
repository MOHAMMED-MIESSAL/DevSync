package com.maven;


import com.maven.model.User;
import com.maven.service.UserService;

public class Main {
    public static void main(String[] args) {
        UserService userService = new UserService();

//        // Créer un  utilisateur
//        User newUser = new User("johndoe", "password123", "John", "Doe", "johndoe@example.com");
//        userService.createUser(newUser);
//
//        // Récupérer et afficher l'utilisateur par ID
//        User user = userService.getUserById(2L);
//        System.out.println("User Retrieved: " + user.getUsername());
//
//        // Mettre à jour un utilisateur
//        user.setLastName("Smith");
//        userService.updateUser(user);

        // Supprimer un utilisateur
//        userService.deleteUser(1L);
    }
}
