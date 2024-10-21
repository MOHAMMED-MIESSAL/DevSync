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
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@WebServlet("/tasks")
public class TaskServlet extends HttpServlet {

    private TaskService taskService = new TaskService();
    private UserService userService = new UserService();
    private TagService tagService = new TagService();

    @Override
    public void init() {
        taskService = new TaskService();
        userService = new UserService();
        tagService = new TagService();
    }


    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Récupérer l'utilisateur connecté (depuis la session)
        HttpSession session = request.getSession(false); // false pour ne pas créer une nouvelle session
        if (session == null) {
            response.sendRedirect(request.getContextPath() + "/login"); // Redirection si la session n'existe pas
            return;
        }
        User currentUser = (User) session.getAttribute("loggedUser");


        if (currentUser == null) {
            response.sendRedirect(request.getContextPath() + "/login"); // Redirection si l'utilisateur n'est pas connecté
            return;
        }

        String taskId = request.getParameter("id");

        if (taskId == null) {
            List<Task> tasks;

            // Vérifier si l'utilisateur est un manager
            if (currentUser.getIsManager()) {
                // Si c'est un manager, récupérer toutes les tâches
                tasks = taskService.findAll();
            } else {
                // Si ce n'est pas un manager, récupérer uniquement les tâches qui lui sont affectées
                tasks = taskService.findTasksByUser(currentUser);
            }

            request.setAttribute("tasks", tasks);
            request.getRequestDispatcher("/Task/listTask.jsp").forward(request, response);

        } else if ("new".equals(taskId)) {
            List<User> users = userService.findAll();
            List<Tag> tags = tagService.findAll();
            request.setAttribute("users", users);
            request.setAttribute("tags", tags);
            request.getRequestDispatcher("/Task/addTask.jsp").forward(request, response);

        } else {
            Optional<Task> taskOpt = taskService.findById(Long.parseLong(taskId));
            if (taskOpt.isPresent()) {
                request.setAttribute("taskToEdit", taskOpt.get());
                List<User> users = userService.findAll();
                request.setAttribute("users", users);
                request.getRequestDispatcher("/Task/editTask.jsp").forward(request, response);
            } else {
                // Gérer le cas où la tâche n'est pas trouvée
                request.setAttribute("errorMessage", "Task not found.");
                request.getRequestDispatcher("/error.jsp").forward(request, response);
            }
        }
    }


    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        User creator = (User) request.getSession().getAttribute("loggedUser");

        if (creator == null) {
            request.setAttribute("errorMessage", "User not logged in.");
            request.getRequestDispatcher("/error.jsp").forward(request, response);
            return;
        }

        if ("add".equalsIgnoreCase(action)) {
            try {
                // Récupération et validation des paramètres
                String description = request.getParameter("task_description");
                String title = request.getParameter("task_name");
                String statusStr = request.getParameter("task_status");
                String dateStr = request.getParameter("dateEcheance");
                String userAffectedIdStr = request.getParameter("userAffected");
                String tagsStr = request.getParameter("tags");

                if (description == null || title == null || statusStr == null || dateStr == null || userAffectedIdStr == null || tagsStr == null) {
                    throw new IllegalArgumentException("Missing required parameters");
                }

                Status status = Status.valueOf(statusStr);
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                LocalDateTime dueDate = LocalDate.parse(dateStr, formatter).atStartOfDay();
                Long userAffectedId = Long.parseLong(userAffectedIdStr);

                Optional<User> userAffectedOpt = userService.findById(userAffectedId);

                if (userAffectedOpt.isPresent()) {
                    User userAffected = userAffectedOpt.get();
                    Set<Tag> tags = getTagsFromString(tagsStr);

                    Task newTask = new Task(creator, dueDate, description, title, status, tags, userAffected);
                    taskService.create(newTask, creator);

                    response.sendRedirect("tasks");
                } else {
                    request.setAttribute("errorMessage", "User affected not found.");
                    request.getRequestDispatcher("/error.jsp").forward(request, response);
                }
            } catch (Exception e) {
                e.printStackTrace();
                request.setAttribute("errorMessage", "An error occurred while adding the task: " + e.getMessage());
                request.getRequestDispatcher("/error.jsp").forward(request, response);
            }
        }
        else if ("update".equalsIgnoreCase(action)) {
            try {
                Long taskId = Long.parseLong(request.getParameter("id"));


                // Récupération et validation des paramètres
                String description = request.getParameter("task_description");
                String title = request.getParameter("task_name");
                String statusStr = request.getParameter("task_status");
                String dateStr = request.getParameter("dateEcheance");
                String userAffectedIdStr = request.getParameter("userAffected");
                String tagsStr = request.getParameter("tags");

                if (description == null || title == null || statusStr == null || dateStr == null || userAffectedIdStr == null || tagsStr == null) {
                    throw new IllegalArgumentException("Missing required parameters");
                }

                Status status = Status.valueOf(statusStr);
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                LocalDateTime dueDate = LocalDate.parse(dateStr, formatter).atStartOfDay();
                Long userAffectedId = Long.parseLong(userAffectedIdStr);

                Optional<User> userAffectedOpt = userService.findById(userAffectedId);
                Optional<Task> taskOpt = taskService.findById(taskId);

                if (userAffectedOpt.isPresent() && taskOpt.isPresent()) {
                    User userAffected = userAffectedOpt.get();
                    Task taskToUpdate = taskOpt.get();
                    Set<Tag> tags = getTagsFromString(tagsStr);

                    taskToUpdate.setName(title);
                    taskToUpdate.setDescription(description);
                    taskToUpdate.setStatus(status);
                    taskToUpdate.setDateEcheance(dueDate);
                    taskToUpdate.setUserAffected(userAffected);
                    taskToUpdate.setTags(tags);

                    taskService.update(taskToUpdate , creator);

                    response.sendRedirect("tasks");
                } else {
                    request.setAttribute("errorMessage", "User affected or Task not found.");
                    request.getRequestDispatcher("/error.jsp").forward(request, response);
                }
            } catch (Exception e) {
                e.printStackTrace();
                request.setAttribute("errorMessage", "An error occurred while updating the task: " + e.getMessage());
                request.getRequestDispatcher("/error.jsp").forward(request, response);
            }

        }
        else if ("delete".equalsIgnoreCase(action)) {
            try {
//                Long taskId = Long.parseLong(request.getParameter("task_id"));
                int taskId = Integer.parseInt(request.getParameter("id"));
                taskService.delete((long) taskId);
                response.sendRedirect("tasks");
            } catch (Exception e) {
                e.printStackTrace();
                request.setAttribute("errorMessage", "An error occurred while deleting the task: " + e.getMessage());
                request.getRequestDispatcher("/error.jsp").forward(request, response);
            }
        }
    }

    // Méthode utilitaire pour récupérer ou créer des tags à partir d'une chaîne de caractères
    private Set<Tag> getTagsFromString(String tagsStr) throws Exception {
        Set<Tag> tags = new HashSet<>();
        if (tagsStr == null || tagsStr.trim().isEmpty()) {
            return tags; // Retourne un ensemble vide si aucun tag n'est fourni
        }

        String[] tagsArray = tagsStr.split(",");

        for (String tagName : tagsArray) {
            tagName = tagName.trim();
            if (!tagName.isEmpty()) {
                try {
                    Optional<Tag> tagOpt = tagService.findByName(tagName);
                    if (tagOpt.isEmpty()) {
                        // Si le tag n'existe pas, on le crée
                        Tag newTag = new Tag(tagName);
                        tagService.create(newTag);
                        tags.add(newTag);
                    } else {
                        // Si le tag existe, on l'ajoute simplement
                        tags.add(tagOpt.get());
                    }
                } catch (IllegalArgumentException e) {
                    // Log l'erreur et continuez avec les autres tags
                    System.err.println("Erreur lors de la création du tag '" + tagName + "': " + e.getMessage());
                }
            }
        }
        return tags;
    }
}

