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


@WebServlet("/")

public class AuthServlet extends HttpServlet {

    private UserService userService = new UserService(); // Injecter ce service serait mieux

    @Override
    public void init() {
        userService = new UserService();
    }


    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("/login.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        try {
            Optional<User> optionalUser = userService.findByUsernameAndPassword(username, password);
            if (optionalUser.isPresent()) {
                User user = optionalUser.get();

                HttpSession session = request.getSession();
                session.setAttribute("loggedUser", user);

                response.sendRedirect(request.getContextPath() + "/tasks");
            } else {

                request.setAttribute("error", "Invalid username or password.");
                request.getRequestDispatcher("/login.jsp").forward(request, response);
            }
        } catch (Exception e) {

            request.setAttribute("error", "An error occurred during login. Please try again.");
            request.getRequestDispatcher("/login.jsp").forward(request, response);
        }
    }

}
