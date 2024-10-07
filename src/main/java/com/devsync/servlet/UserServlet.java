package com.devsync.servlet;

import com.devsync.entity.User;
import com.devsync.service.UserService;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@WebServlet("/users")
public class UserServlet extends HttpServlet {
    private UserService userService;

    @Override
    public void init() {
        userService = new UserService();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String userId = request.getParameter("id");

        if (userId == null) {
            List<User> users = userService.findAll();
            request.setAttribute("users", users);
            request.getRequestDispatcher("/listUser.jsp").forward(request, response);
        } else {
            Optional<User> optionalUser = userService.findById(Long.parseLong(userId));

            if (optionalUser.isPresent()) {
                User user = optionalUser.get();
                request.setAttribute("userToEdit", user);
                request.getRequestDispatcher("/editUser.jsp").forward(request, response);
            } else {
                request.setAttribute("error", "User not found");
                request.getRequestDispatcher("/erreur.jsp").forward(request, response);
            }
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String action = request.getParameter("action");

        if ("delete".equals(action)) {
            String userId = request.getParameter("userId");
            userService.delete(Long.parseLong(userId));
            response.sendRedirect("users");
        } else if ("update".equals(action)) {
            String userId = request.getParameter("userId");
            String username = request.getParameter("username");
            String password = request.getParameter("password");
            String firstName = request.getParameter("firstName");
            String lastName = request.getParameter("lastName");
            String email = request.getParameter("email");

            boolean isManager = request.getParameter("isManager") != null;


            User user = new User(Long.parseLong(userId), username, password, firstName, lastName, email, isManager);
            userService.update(user);
            response.sendRedirect("users");
        } else {
            String username = request.getParameter("username");
            String password = request.getParameter("password");
            String firstName = request.getParameter("firstName");
            String lastName = request.getParameter("lastName");
            String email = request.getParameter("email");

            String isManagerInput = request.getParameter("isManager");
            boolean isManager = Objects.equals(isManagerInput, "true");

            User user = new User(username, password, firstName, lastName, email, isManager);
            userService.create(user);
            response.sendRedirect("users");
        }
    }

}
