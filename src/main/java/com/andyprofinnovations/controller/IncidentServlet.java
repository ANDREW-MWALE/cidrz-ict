package com.andyprofinnovations.controller;

import com.andyprofinnovations.dao.IncidentDAO;
import com.andyprofinnovations.dao.LocationDAO;
import com.andyprofinnovations.model.Incident;
import com.andyprofinnovations.model.Location;
import com.andyprofinnovations.model.Users;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

//@WebServlet("/IncidentServlet")
public class IncidentServlet extends HttpServlet {
    private IncidentDAO incidentDAO;
    private LocationDAO locationDAO;

    @Override
    public void init() throws ServletException {
        super.init();
        incidentDAO = new IncidentDAO();
        locationDAO = new LocationDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        if (action == null) {
            action = "";
        }

        switch (action) {
            case "new":
                try {
                    showNewForm(request, response);
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
                break;
            case "insert":
                insertIncident(request, response);
                break;
            case "edit":
                showEditForm(request, response);
                break;
            case "update":
                updateIncident(request, response);
            case "delete":
                deleteIncident(request, response);
                break;
            default:
                listIncidents(request, response);
                break;
        }
    }

    private void updateIncident(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
                 Incident incident = new Incident();
             incident.setName(request.getParameter("name"));
             incident.setDescription(request.getParameter("description"));
             incident.setCauses(request.getParameter("causes"));
             incident.setLocation_id(Integer.parseInt(request.getParameter("location_id")));
        incidentDAO.editIncident(incident);


        response.sendRedirect("?action=list");

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        try{
            insertIncident(request, response);
        } catch (ServletException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
    private void listIncidents(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //List<Incident> incidents = incidentDAO.listIncident();
        //request.setAttribute("incidents", incidents);
       // request.getRequestDispatcher("incident-list.jsp").forward(request, response);

        // Implement listing incidents logic

    }

    private void showNewForm(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException {
        RequestDispatcher dispatcher = request.getRequestDispatcher("incident-form.jsp");
        dispatcher.forward(request, response);
    }

        private void showEditForm(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int incidentID= Integer.parseInt(request.getParameter("incident_id"));
        request.getRequestDispatcher("showEditForm" + incidentID );
        Incident existingIncident = incidentDAO.findIncident(String.valueOf(incidentID));
        request.setAttribute("incidents", existingIncident);
        RequestDispatcher dispatcher = request.getRequestDispatcher("incident-form.jsp");
        dispatcher.forward(request, response);
        // Implement showing edit form logic
    }

    private void insertIncident(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8"); // Ensure UTF-8 encoding

        // Retrieve form parameters
        String name = request.getParameter("name");
        String description = request.getParameter("description");
        String causes = request.getParameter("causes");
        int location_id = Integer.parseInt(request.getParameter("location_id"));


        HttpSession session = request.getSession();
        Users loggedInUser = (Users) session.getAttribute("loggedInUser");

        if (loggedInUser == null) {
            // Handle the case where the user is not logged in
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "You must be logged in to submit an incident.");
            return;
        }

        String createdBy = String.valueOf(loggedInUser.getUser_id());
        String updatedBy = createdBy; // Assuming the incident is created and updated by the same user initially

        // Generate timestamps
        Timestamp currentTimestamp = new Timestamp(System.currentTimeMillis());

        // Create new Incident object
        Incident incident = new Incident();
        incident.setName(name);
        incident.setDescription(description);
        incident.setCauses(causes);
        incident.setLocation_id(location_id);
        incident.setCreated_by(createdBy);
        incident.setCreated_date(currentTimestamp);
        incident.setUpdated_by(updatedBy);
        incident.setLast_updated_date(currentTimestamp);

        try {
            boolean success = incidentDAO.addIncident(incident);

            if (success) {
                // Redirect to the list page after successful insertion
                response.sendRedirect("incident-list.jsp");
            } else {
                // Handle insertion failure
                response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Failed to add incident.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new ServletException("Database error: " + e.getMessage(), e);
        } catch (Exception e) {
            e.printStackTrace();
            throw new ServletException("Unexpected error: " + e.getMessage(), e);
        }
    }
    private void deleteIncident(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        incidentDAO.deleteIncident(Integer.valueOf(request.getParameter("incident_id")));
        response.sendRedirect("incident-list.jsp");
}
}