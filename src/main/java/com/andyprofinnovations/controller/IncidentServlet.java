package com.andyprofinnovations.controller;

import com.andyprofinnovations.dao.IncidentDAO;
import com.andyprofinnovations.dao.LocationDAO;
import com.andyprofinnovations.model.Incident;
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
                    showNewForm(request, response);
                break;
            case "insert":

                    try {
                        insertIncident(request, response);
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }
                break;
            case "edit":
                showEditForm(request, response);
                break;
            case "update":
                    updateIncident(request, response);
                break;
            case "delete":
                    deleteIncident(request, response);

                break;
            default:

                    listIncidents(request, response);

                break;
        }
    }

    private void listIncidents(HttpServletRequest request, HttpServletResponse response) {
                    //the login is in createIncidentServlet
    }

    private void updateIncident(HttpServletRequest request, HttpServletResponse response) {
        try {
            Incident incident = new Incident();
            incident.setIncident_id(Integer.parseInt(request.getParameter("incident_id")));
            incident.setName(request.getParameter("name"));
            incident.setDescription(request.getParameter("description"));
            incident.setCauses(request.getParameter("causes"));
            incident.setLocation_id(Integer.parseInt(request.getParameter("location_id")));
            incident.setCreated_by(request.getParameter("created_by"));

            // Handle potential null or empty strings for timestamps
            String createdDateStr = request.getParameter("created_date");
            String lastUpdatedDateStr = request.getParameter("last_updated_date");
            Timestamp createdDate = null;
            Timestamp lastUpdatedDate = null;

            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

            if (createdDateStr != null && !createdDateStr.isEmpty()) {
                try {
                    java.util.Date parsedDate = dateFormat.parse(createdDateStr);
                    createdDate = new Timestamp(parsedDate.getTime());
                } catch (ParseException e) {
                    throw new RuntimeException("Invalid date format for created_date", e);
                }
            }

            if (lastUpdatedDateStr != null && !lastUpdatedDateStr.isEmpty()) {
                try {
                    java.util.Date parsedDate = dateFormat.parse(lastUpdatedDateStr);
                    lastUpdatedDate = new Timestamp(parsedDate.getTime());
                } catch (ParseException e) {
                    throw new RuntimeException("Invalid date format for last_updated_date", e);
                }
            }

            incident.setCreated_date(createdDate);
            incident.setLast_updated_date(lastUpdatedDate);
            incident.setUpdated_by(request.getParameter("updated_by"));

            boolean result = incidentDAO.editIncident(incident);

            System.out.println(result);
            response.sendRedirect("incident-list.jsp");

        } catch (NumberFormatException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void showEditForm(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int incidentID= Integer.parseInt(request.getParameter("incident_id"));
         request.getRequestDispatcher("showEditForm" + incidentID );
         Incident existingIncident = incidentDAO.findIncident(String.valueOf(incidentID));
        request.setAttribute("incidents", existingIncident);
         RequestDispatcher dispatcher = request.getRequestDispatcher("updateIncident.jsp");
        dispatcher.forward(request, response);

    }

    private void insertIncident(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException, SQLException {
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
        String updatedBy = createdBy; // Assuming the incident is created and updated by the same user initially.
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

        incidentDAO.addIncident(incident);

        response.sendRedirect("incident-list.jsp");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try{
            insertIncident(request, response);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void showNewForm(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        RequestDispatcher dispatcher = request.getRequestDispatcher("incident-form.jsp");
        dispatcher.forward(request, response);
    }
    //method to delete an item from the database
        private void deleteIncident(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        incidentDAO.deleteIncident(Integer.valueOf(request.getParameter("incident_id")));
        response.sendRedirect("incident-list.jsp");
}

}

