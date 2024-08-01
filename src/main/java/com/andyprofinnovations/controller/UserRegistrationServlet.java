package com.andyprofinnovations.controller;

import com.andyprofinnovations.dao.UserDAO;
import com.andyprofinnovations.model.RequestForChange;
import com.andyprofinnovations.model.Users;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class UserRegistrationServlet extends HttpServlet {
    private UserDAO userDAO;

    public void init(){
        userDAO = new UserDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String actions = request.getParameter("actions");

        if(actions==null){
            actions = "";
        }
        switch (actions){
            case "edit":
                showEditForm(request, response);
                break;
            case "update":
                try {
                    updateUser(request, response);
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
                break;
            case "delete":
                deleteUser(request, response);
            default:
                listUser(request, response);
        }

    }

    private void listUser(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Users> users = userDAO.userList();
        request.setAttribute("users", users);
        request.getRequestDispatcher("users_list.jsp").forward(request, response);
    }

    private void deleteUser(HttpServletRequest request, HttpServletResponse response) {
        userDAO.deleteUsers(request.getParameter("user_id"));
    }

    private void updateUser(HttpServletRequest request, HttpServletResponse response) throws SQLException {
        Users users = new Users();
        users.setUser_id(Integer.parseInt(request.getParameter("user_id")));
        users.setName(request.getParameter("name"));
        users.setPosition(request.getParameter("position"));
        users.setEmail(request.getParameter("email"));
        users.setRole_id(Integer.parseInt(request.getParameter("role_id")));
        users.setPassword(request.getParameter("password"));

        userDAO.editUpdateUser(users);
    }

    private void showEditForm(HttpServletRequest request, HttpServletResponse response) {
           request.getRequestDispatcher("edit-registration-form.jsp");
    }
}
