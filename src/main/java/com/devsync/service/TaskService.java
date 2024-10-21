package com.devsync.service;

import com.devsync.entity.Task;
import com.devsync.entity.User;
import com.devsync.enums.Status;
import com.devsync.exceptions.ResourceNotFoundException;
import com.devsync.exceptions.TaskCreationException;
import com.devsync.exceptions.ValidationException;
import com.devsync.repository.implementations.ReplacementRequestRepositoryImpl;
import com.devsync.repository.implementations.TaskRepositoryImpl;
import com.devsync.repository.implementations.UserRepositoryImpl;
import com.devsync.repository.interfaces.TaskRepository;
import com.devsync.repository.interfaces.UserRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public class TaskService {
    private final TaskRepository taskRepository;
    private final UserRepository userRepository ;
    private final ReplacementRequestService replacementRequestService;

    public TaskService( ) {
        this.taskRepository = new TaskRepositoryImpl();
        this.userRepository = new UserRepositoryImpl();
        this.replacementRequestService = new ReplacementRequestService();
    }

    public void create(Task task, User currentUser) {

        if (!currentUser.getIsManager() && !task.getUserAffected().getId().equals(currentUser.getId())) {
            throw new ValidationException("Vous ne pouvez attribuer des tâches qu'à vous-même.");
        }

        if (task.getDateEcheance() != null && task.getDateEcheance().isAfter(LocalDate.now().plusDays(3).atStartOfDay())) {
            throw new TaskCreationException("La tâche ne peut pas être planifiée à plus de 3 jours à l'avance.");
        }

        if (task.getDateCreation() != null && task.getDateCreation().isBefore(LocalDate.now().atStartOfDay())) {
            throw new TaskCreationException("La tâche ne peut pas être créée avec une date dans le passé.");
        }

        if (task.getName() == null || task.getName().isEmpty()) {
            throw new ValidationException("Le titre de la tâche ne peut pas être vide.");
        }

        if (task.getDescription() == null || task.getDescription().isEmpty()) {
            throw new ValidationException("La description de la tâche ne peut pas être vide.");
        }

        if (task.getStatus() == null) {
            throw new ValidationException("Le statut de la tâche ne peut pas être vide.");
        }

        if (task.getUserAffected() == null || task.getUserAffected().getId() == null) {
            throw new ValidationException("L'utilisateur affecté à la tâche ne peut pas être vide.");
        }

        if (task.getTags() == null || task.getTags().size() < 2) {
            throw new ValidationException("La tâche doit avoir au moins deux tags.");
        }

        if (task.getDateCreation() == null) {
            task.setDateCreation(LocalDateTime.now());
        }

        // Création de la tâche
        taskRepository.create(task);
    }

    public void update(Task task, User currentUser) {

        Optional<Task> existingTaskOpt = taskRepository.findById(task.getId());
        if (existingTaskOpt.isEmpty()) {
            throw new ResourceNotFoundException("La tâche avec l'ID " + task.getId() + " n'existe pas.");
        }

        Task existingTask = existingTaskOpt.get();

        if (!currentUser.getIsManager() && !task.getUserAffected().getId().equals(currentUser.getId())) {
            throw new ValidationException("Vous ne pouvez réattribuer cette tâche qu'à vous-même.");
        }

        if (task.getStatus() != Status.COMPLETED && LocalDateTime.now().isAfter(existingTask.getDateEcheance())) {
            throw new ValidationException("La tâche doit être marquée comme terminée avant la date limite.");
        }

        if (task.getDateEcheance() != null && task.getDateEcheance().isAfter(LocalDate.now().plusDays(3).atStartOfDay())) {
            throw new TaskCreationException("La tâche ne peut pas être planifiée à plus de 3 jours à l'avance.");
        }

        if (task.getName() == null || task.getName().isEmpty()) {
            throw new ValidationException("Le titre de la tâche ne peut pas être vide.");
        }

        if (task.getDescription() == null || task.getDescription().isEmpty()) {
            throw new ValidationException("La description de la tâche ne peut pas être vide.");
        }

        if (task.getStatus() == null) {
            throw new ValidationException("Le statut de la tâche ne peut pas être vide.");
        }

        if (task.getUserAffected() == null || task.getUserAffected().getId() == null) {
            throw new ValidationException("L'utilisateur affecté à la tâche ne peut pas être vide.");
        }

        if (task.getTags() == null || task.getTags().isEmpty()) {
            throw new ValidationException("La tâche doit avoir au moins un tag.");
        }

        task.setDateCreation(existingTask.getDateCreation());

        taskRepository.update(task);
    }

    public void delete(Long id) {
        // Vérifier si la tâche existe avant de la supprimer
        Optional<Task> task = taskRepository.findById(id);
        if (task.isEmpty()) {
            throw new ResourceNotFoundException("La tâche avec l'ID " + id + " n'existe pas.");
        }
        taskRepository.delete(id);
    }


    public List<Task> findAll() {
        return taskRepository.findAll();
    }

    public Optional<Task> findById(Long id) {
        return taskRepository.findById(id);
    }

    public List<Task> findTasksByUser(User user) {
        return taskRepository.findTasksByUser(user);
    }



    // Méthodes pour les jetons

    public void requestTaskReplacement(Task task, User requester) {
        // Vérifie que la tâche existe
        Optional<Task> existingTaskOpt = taskRepository.findById(task.getId());
        if (existingTaskOpt.isEmpty()) {
            throw new ResourceNotFoundException("La tâche n'existe pas.");
        }

        // Vérifie si l'utilisateur a suffisamment de jetons
        if (requester.getDailyTokens() <= 0) {
            throw new ValidationException("Vous n'avez plus de jetons pour demander un remplacement aujourd'hui.");
        }

        // Soumettre une demande de remplacement via le service
        replacementRequestService.submitRequest(task, requester);

        // Décrémente le nombre de jetons quotidiens
        requester.setDailyTokens(requester.getDailyTokens() - 1);
        userRepository.update(requester); // Met à jour l'utilisateur dans la base de données
    }


    public void deleteTask(Task task, User currentUser) {

        Optional<Task> existingTaskOpt = taskRepository.findById(task.getId());
        if (existingTaskOpt.isEmpty()) {
            throw new ResourceNotFoundException("La tâche à supprimer n'existe pas.");
        }

        Task existingTask = existingTaskOpt.get();


        Long creatorId = existingTask.getCreator().getId();


        if (!creatorId.equals(currentUser.getId())) {
            // Vérifie que l'utilisateur a un jeton mensuel disponible
            if (currentUser.getMonthlyTokens() <= 0) {
                throw new ValidationException("Vous n'avez plus de jetons mensuels pour supprimer une tâche.");
            }

            currentUser.setMonthlyTokens(currentUser.getMonthlyTokens() - 1);
            userRepository.update(currentUser);
        }

        // Supprime la tâche
        taskRepository.delete(task.getId());
    }




}
