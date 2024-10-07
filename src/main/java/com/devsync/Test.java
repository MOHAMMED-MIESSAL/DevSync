package com.devsync;

import com.devsync.entity.User;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class Test {
    public static void main(String[] args) {
        // Créer une instance de l'EntityManagerFactory
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("myJPAUnit");
        EntityManager em = emf.createEntityManager();

        // Démarrer une transaction
        em.getTransaction().begin();

        // Créer un nouvel utilisateur
        User user = new User();
        user.setUsername("john_doe");
        user.setPassword("password123");
        user.setFirstName("John");
        user.setLastName("Doe");
        user.setEmail("john.doe@example.com");
        user.setIsManager(false);

        // Persister l'utilisateur
        em.persist(user);

        // Valider la transaction
        em.getTransaction().commit();

        // Fermer l'EntityManager
        em.close();
        emf.close();

        System.out.println("Utilisateur inséré avec succès : " + user.getUsername());
    }
}
