package com.devsync;

import com.devsync.entity.Task;
import com.devsync.entity.Tag;
import com.devsync.entity.User;
import com.devsync.enums.Status;
import com.devsync.service.TaskService;
import com.devsync.service.TagService;
import com.devsync.service.UserService;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

public class Test {
    public static void main(String[] args) {



        // Créer une instance de l'EntityManagerFactory
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("myJPAUnit");
        EntityManager em = emf.createEntityManager();

        // Services
        UserService userService = new UserService();
        TaskService taskService = new TaskService();
        TagService tagService = new TagService(); // Service pour les tags

        try {
            // Démarrer une transaction
            em.getTransaction().begin();

            // Récupérer des utilisateurs
            Optional<User> userOpt = userService.findById(1L);
            Optional<User> user2Opt = userService.findById(2L);

            if (userOpt.isPresent() && user2Opt.isPresent()) {
                User user1 = userOpt.get();
                User user2 = user2Opt.get();

                // Créer une nouvelle tâche
                Task task = new Task();
                task.setCreator(user1); // L'utilisateur qui a créé la tâche
                task.setDateCreation(java.time.LocalDateTime.now());
                task.setName("Tâche de Développement");
                task.setStatus(Status.NOT_STARTED);
                task.setUtilisateurAffecte(user2); // Utilisateur assigné à la tâche

                // Gestion des tags : création ou récupération
                Tag tag1 = tagService.findById(1L).orElseGet(() -> {
                    Tag newTag = new Tag();
                    newTag.setName("Urgent");
                    tagService.create(newTag);
                    return newTag;
                });

                Tag tag2 = tagService.findById(2L).orElseGet(() -> {
                    Tag newTag = new Tag();
                    newTag.setName("Important");
                    tagService.create(newTag);
                    return newTag;
                });

                // Ajouter les tags à la tâche
                Set<Tag> tags = new HashSet<>();
                tags.add(tag1);
                tags.add(tag2);
                task.setTags(tags);

                // Persister la tâche avec les tags
                taskService.create(task);

                System.out.println("Tâche créée avec succès avec les tags !");
            } else {
                System.out.println("Les utilisateurs spécifiés n'existent pas.");
            }

            // Valider la transaction
            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback(); // Annuler la transaction en cas d'erreur
            e.printStackTrace();
        } finally {
            // Fermer l'EntityManager
            em.close();
            emf.close();
        }
    }
}
