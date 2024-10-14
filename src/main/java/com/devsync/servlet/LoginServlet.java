package com.devsync.servlet;

import com.devsync.entity.User;
import com.devsync.service.UserService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.Optional;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {

    private UserService userService = new UserService(); // Injecter ce service serait mieux

    @Override
    public void init() {
        userService = new UserService();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Redirection vers la page login.jsp pour le formulaire
        request.getRequestDispatcher("/login.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        try {
            // Authentifie l'utilisateur en vérifiant le nom d'utilisateur et le mot de passe
            Optional<User> optionalUser = userService.findByUsernameAndPassword(username, password);
            if (optionalUser.isPresent()) {
                User user = optionalUser.get();
                // Stocker l'utilisateur connecté dans la session
                HttpSession session = request.getSession();
                session.setAttribute("loggedUser", user);

                // Redirection après authentification réussie
                response.sendRedirect(request.getContextPath() + "/taches");
            } else {
                // En cas d'échec, afficher un message d'erreur
                request.setAttribute("error", "Invalid username or password.");
                request.getRequestDispatcher("/login.jsp").forward(request, response);
            }
        } catch (Exception e) {
            // En cas d'exception, afficher un message d'erreur
            request.setAttribute("error", e.getMessage());
            request.getRequestDispatcher("/login.jsp").forward(request, response);
        }
    }
}
