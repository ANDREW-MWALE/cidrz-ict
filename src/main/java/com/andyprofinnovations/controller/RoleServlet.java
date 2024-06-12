package com.andyprofinnovations.controller;

import com.andyprofinnovations.dao.RoleDAO;
import com.andyprofinnovations.model.Incident;
import com.andyprofinnovations.model.Role;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Array;
import java.sql.Date;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class RoleServlet extends HttpServlet {
    private RoleDAO roleDAO;

    public void init(){
        roleDAO = new RoleDAO();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
        String action = request.getParameter("action");
        if(action!= null){
            switch (action){
                case "new":
                    showNewForm(request,response);
                    break;
                case "insert":
                    try {
                        insertRole(request, response);
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }
                    break;
                case "edit":
                    showEditForm(request, response);
                    break;
                case "update":
                    updateRole(request, response);
                    break;
                case "delete":
                    deleteRole(request, response);
                    break;
                default:
                    roleList(request,response);

            }



        }
    }

    private void updateRole(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
        Role role = new Role();
        role.setName(request.getParameter("name"));
        role.setRole_id(Integer.parseInt(request.getParameter("role_id")));
        role.setDescription(request.getParameter("title"));
        try {
            roleDAO.editRole(role);
        } catch (Exception ex) {
            throw  new RuntimeException("role not not found");
        }
        response.sendRedirect("?action=list");
    }
    private void roleList(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException{
        List<Role> listRole = roleDAO.listRole();
        request.setAttribute("listRole", listRole);
        RequestDispatcher dispatcher = request.getRequestDispatcher("role-list.jsp");
        dispatcher.forward(request, response);


    }

    private void deleteRole(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
         roleDAO.deleteRole(Integer.parseInt(request.getParameter("role_id")));
         response.sendRedirect("?action=list");

    }

    private void showEditForm(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Integer roleID = Integer.valueOf(request.getParameter("role_id"));
        System.out.println("showEditForm" + roleID);
        Role existingRole = roleDAO.findRole(String.valueOf(roleID));
        request.setAttribute("role", existingRole);
        RequestDispatcher dispatcher = request.getRequestDispatcher("role-form.jsp");
        dispatcher.forward(request, response);


    }

    private void insertRole(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException {
        String name = request.getParameter("name");
        String description = request.getParameter("description");

        Role role = new Role();
        role.setName(name);
        role.setDescription(description);

        roleDAO.addRole(role);


    }

    private void showNewForm(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("Role-form.jsp");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String name = request.getParameter("name");
        String description = request.getParameter("description");


        // Create new Incident object
        Role role = new Role();
        role.setName(name);
        role.setDescription(description);

        response.sendRedirect("?action=list");

    }
}
