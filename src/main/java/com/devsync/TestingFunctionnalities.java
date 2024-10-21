package com.devsync;

import com.devsync.entity.Tag;
import com.devsync.entity.Task;
import com.devsync.entity.User;
import com.devsync.enums.Status;
import com.devsync.service.TagService;
import com.devsync.service.TaskService;
import com.devsync.service.UserService;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

public class TestingFunctionnalities {

    public static void main(String[] args) {
        // Instantiation des services
        UserService userService = new UserService();
        TaskService taskService = new TaskService();
        TagService tagService = new TagService();  // Ajout du TagService

        // Création d'un set pour les tags
        Set<Tag> tags = new HashSet<>();

        // Utilisation de la méthode getOrCreateTag pour gérer les tags
        Tag tag1 = getOrCreateTag(tagService, "Urgent");
        tags.add(tag1);

        Tag tag2 = getOrCreateTag(tagService, "Important");
        tags.add(tag2);

        // Supposant qu'ils existent déjà dans la base de données
        User creator = userService.findById(1L).get();
        User userAffected = userService.findById(2L).get();

        // Date d'échéance
        LocalDateTime dateEcheance = LocalDateTime.now().plusDays(1);

        // Création de la tâche avec les tags
        Task newTask = new Task(
                creator,
                dateEcheance,
                "Description de la tâche de test",
                "Tâche de test",
                Status.NOT_STARTED,
                tags,
                userAffected
        );

        // Ajout de la tâche
        taskService.create(newTask, creator);

        System.out.println("Tâche créée avec succès : " + newTask);
    }

    // Méthode utilitaire pour trouver ou créer un tag
    private static Tag getOrCreateTag(TagService tagService, String tagName) {
        Optional<Tag> existingTag = tagService.findByName(tagName);
        return existingTag.orElseGet(() -> {
            Tag newTag = new Tag();
            newTag.setName(tagName);
            tagService.create(newTag);  // Persist du nouveau tag dans la base de données
            return newTag;
        });
    }
}
