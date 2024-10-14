package com.devsync.servlet;

import com.devsync.entity.Tag;
import com.devsync.entity.Task;
import com.devsync.entity.User;

import com.devsync.enums.Status;

import com.devsync.service.TagService;
import com.devsync.service.TaskService;
import com.devsync.service.UserService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.*;

@WebServlet("/taches")
public class TaskServlet extends HttpServlet {
    private TaskService taskService;
    private UserService userService;
    private TagService tagService;

    @Override
    public void init() {
        taskService = new TaskService();
        userService = new UserService();
        tagService = new TagService();
    }


    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String tacheId = request.getParameter("id");

        if (tacheId == null) {
            List<Task> taches = taskService.findAll();
            request.setAttribute("taches", taches);
            request.getRequestDispatcher("/tache/listTache.jsp").forward(request, response);
        }  else if ("new".equals(tacheId)) {
            List<User> users = userService.findAll();
            List<Tag> tags = tagService.findAll();
            request.setAttribute("users", users);
            request.setAttribute("tags", tags);
            request.getRequestDispatcher("/tache/addTache.jsp").forward(request, response);
        }
        else {
            Optional<Task> optionalTache = taskService.findById(Long.parseLong(tacheId));

            if (optionalTache.isPresent()) {
                Task task = optionalTache.get();
                request.setAttribute("tacheToEdit", task);
                List<User> users = userService.findAll();
                request.setAttribute("users", users);
                request.getRequestDispatcher("/tache/editTache.jsp").forward(request, response);
            } else {
                request.setAttribute("error", "Tache not found");
                request.getRequestDispatcher("/erreur.jsp").forward(request, response);
            }
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Récupérer l'action à effectuer (add, edit, delete)
        String action = request.getParameter("action");

        if (action == null) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Action non spécifiée.");
            return;
        }

        try {
            switch (action) {
                case "add":
                    handleAddTask(request, response);
                    break;
                case "edit":
                    handleEditTask(request, response);
                    break;
                case "delete":
                    handleDeleteTask(request, response);
                    break;
                default:
                    response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Action inconnue.");
            }
        } catch (Exception e) {
            throw new ServletException("Erreur lors du traitement de la tâche.", e);
        }
    }

    private void handleAddTask(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        // Récupérer les données du formulaire
        String taskName = request.getParameter("name");
        String status = request.getParameter("status");
        String creatorId = request.getParameter("creatorId");
        String assignedUserId = request.getParameter("utilisateurAffecte");
        String tagInput = request.getParameter("tags");

        // Vérifiez si creatorId et assignedUserId ne sont pas nuls ou vides
        if (creatorId == null || creatorId.isEmpty() || assignedUserId == null || assignedUserId.isEmpty()) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "ID du créateur ou utilisateur assigné manquant.");
            return;
        }

        // Récupérer les utilisateurs associés
        Optional<User> creatorOpt = userService.findById(Long.parseLong(creatorId));
        Optional<User> assignedUserOpt = userService.findById(Long.parseLong(assignedUserId));

        if (creatorOpt.isEmpty() || assignedUserOpt.isEmpty()) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Créateur ou utilisateur assigné invalide.");
            return;
        }

        User creator = creatorOpt.get();
        User assignedUser = assignedUserOpt.get();

        // Créer la tâche
        Task task = new Task();
        task.setName(taskName);
        task.setStatus(Status.valueOf(status));
        task.setCreator(creator);
        task.setUtilisateurAffecte(assignedUser);
        task.setDateCreation(java.time.LocalDateTime.now());

        // Ajouter les tags
        Set<Tag> tags = processTags(tagInput);
        task.setTags(tags);

        // Persister la tâche
        taskService.create(task);

        // Rediriger après ajout
        response.sendRedirect("tasks");
    }

    private void handleEditTask(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        // Récupérer les données du formulaire
        String taskId = request.getParameter("taskId");
        String taskName = request.getParameter("name");
        String status = request.getParameter("status");
        String assignedUserId = request.getParameter("utilisateurAffecte");
        String tagInput = request.getParameter("tags");

        // Récupérer la tâche existante
        Optional<Task> taskOpt = taskService.findById(Long.parseLong(taskId));
        if (taskOpt.isEmpty()) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND, "Tâche non trouvée.");
            return;
        }

        Task task = taskOpt.get();

        // Récupérer l'utilisateur assigné
        Optional<User> assignedUserOpt = userService.findById(Long.parseLong(assignedUserId));
        if (assignedUserOpt.isEmpty()) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Utilisateur assigné invalide.");
            return;
        }

        User assignedUser = assignedUserOpt.get();

        // Mettre à jour les données de la tâche
        task.setName(taskName);
        task.setStatus(Status.valueOf(status));
        task.setUtilisateurAffecte(assignedUser);

        // Mise à jour des tags
        Set<Tag> tags = processTags(tagInput);
        task.setTags(tags);

        // Persister les modifications
        taskService.update(task);

        // Rediriger après édition
        response.sendRedirect("taches");
    }

    private void handleDeleteTask(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        // Récupérer l'ID de la tâche à supprimer
        String taskId = request.getParameter("taskId");

        // Vérifier si la tâche existe
        Optional<Task> taskOpt = taskService.findById(Long.parseLong(taskId));
        if (taskOpt.isEmpty()) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND, "Tâche non trouvée.");
            return;
        }

        Task task = taskOpt.get();

        // Supprimer la tâche
        taskService.delete(task.getId());

        // Rediriger après suppression
        response.sendRedirect("taches");
    }

    private Set<Tag> processTags(String tagInput) {
        Set<Tag> tags = new HashSet<>();
        if (tagInput != null && !tagInput.isEmpty()) {
            String[] tagNames = tagInput.split(",");
            for (String tagName : tagNames) {
                tagName = tagName.trim();

                // Chercher le tag dans la base de données
//                Optional<Tag> existingTag = tagService.findByName(tagName);

                Tag tag;
//                if (existingTag.isPresent()) {
//                    tag = existingTag.get();  // Si le tag existe déjà, on le récupère
//                } else {
                    tag = new Tag();  // Sinon, on crée un nouveau tag
                    tag.setName(tagName);
                    tagService.create(tag);  // Persister le nouveau tag
//                }

                tags.add(tag);  // Ajouter le tag (existant ou nouveau) au set
            }
        }
        return tags;
    }

}

