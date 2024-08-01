package com.andyprofinnovations.controller;

import com.andyprofinnovations.dao.UserDAO;
import com.andyprofinnovations.model.Users;
import com.andyprofinnovations.util.PasswordUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

public class UserServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private UserDAO userDAO;

    public void init() {
        userDAO = new UserDAO();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");

        if (action != null) {
            switch (action) {
                case "register":
                    registerUser(request, response);
                    break;
                case "login":
                    loginUser(request, response);
                    break;
                default:
                    listUsers(request, response);
                    break;
            }
        }
    }

    private void listUsers(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Users> users = userDAO.userList();
        request.setAttribute("users", users);

        request.getRequestDispatcher("edit-registration-form.jsp").forward(request, response);
    }

    private void registerUser(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String name = request.getParameter("name");
        String position = request.getParameter("position");
        String email = request.getParameter("email");
        int roleId = Integer.parseInt(request.getParameter("role_id")); // Assuming role_id is passed as a parameter
        String password = request.getParameter("password");
        String confirmPassword = request.getParameter("confirm_password");

        if (!password.equals(confirmPassword)) {
            // Passwords do not match, handle error
            request.setAttribute("errorMessage", "Passwords do not match");
            request.getRequestDispatcher("registration.jsp").forward(request, response);
            return;
        }

        // Passwords match, proceed with registration
        String hashedPassword = PasswordUtil.hashPassword(password); // Hash the password
        Users newUser = new Users(0, name, position, email, roleId, hashedPassword); // Use the hashed password
        userDAO.addUser(newUser);

        response.sendRedirect("registration_success.jsp");
    }

    private void loginUser(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String email = request.getParameter("email");
        String password = request.getParameter("password");

        String hashedPassword = PasswordUtil.hashPassword(password); // Hash the entered password

        Users user = userDAO.getUserByEmailAndPassword(email, hashedPassword); // Compare hashed password
        if (user != null) {
            HttpSession session = request.getSession();
            session.setAttribute("loggedInUser", user);
            session.setAttribute("userRole", user.getRole_id()); // Store user's role
            response.sendRedirect("home.jsp");
        } else {
            response.sendRedirect("login_failure.jsp");
        }
    }
}
