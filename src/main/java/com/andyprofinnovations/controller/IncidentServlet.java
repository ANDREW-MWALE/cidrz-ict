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

    private void listIncidents(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Implement this method to list incidents
        // For example:
        // List<Incident> incidents = incidentDAO.listIncident();
        // request.setAttribute("incidentList", incidents);
        // RequestDispatcher dispatcher = request.getRequestDispatcher("incident-list.jsp");
        // dispatcher.forward(request, response);
    }

    private void updateIncident(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            Incident incident = new Incident();
            incident.setIncident_id(Integer.parseInt(request.getParameter("incident_id")));
            incident.setName(request.getParameter("name"));
            incident.setDescription(request.getParameter("description"));
            incident.setCauses(request.getParameter("causes"));
            incident.setLocation_id(Integer.parseInt(request.getParameter("location_id")));
            incident.setCreated_by(request.getParameter("created_by"));

            // Log received parameters
            System.out.println("Received parameters:");
            System.out.println("incident_id: " + incident.getIncident_id());
            System.out.println("name: " + incident.getName());
            System.out.println("description: " + incident.getDescription());
            System.out.println("causes: " + incident.getCauses());
            System.out.println("location_id: " + incident.getLocation_id());
            System.out.println("created_by: " + incident.getCreated_by());

            // Handle potential null or empty strings for timestamps
            String createdDateStr = request.getParameter("created_date");
            String lastUpdatedDateStr = request.getParameter("last_updated_date");
            Timestamp createdDate = null;
            Timestamp lastUpdatedDate = new Timestamp(System.currentTimeMillis());

            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

            if (createdDateStr != null && !createdDateStr.isEmpty()) {
                try {
                    java.util.Date parsedDate = dateFormat.parse(createdDateStr);
                    createdDate = new Timestamp(parsedDate.getTime());
                } catch (ParseException e) {
                    throw new RuntimeException("Invalid date format for created_date", e);
                }
            }

            incident.setCreated_date(createdDate);
            incident.setLast_updated_date(lastUpdatedDate);
            incident.setUpdated_by(request.getParameter("updated_by"));

            // Set approval status
            String Status = request.getParameter("status");
            incident.setStatus("");
            incident.setStatus("");

            boolean result = incidentDAO.editIncident(incident);

            System.out.println("Update result: " + result);
            response.sendRedirect("incident-list.jsp");

        } catch (NumberFormatException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void showEditForm(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int incidentID = Integer.parseInt(request.getParameter("incident_id"));
        Incident existingIncident = incidentDAO.findIncident(String.valueOf(incidentID));
        request.setAttribute("incident", existingIncident);
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
//        String status = request.getParameter("status");

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
//        incident.setStatus(status); // Default status for new incidents

        incidentDAO.addIncident(incident);

        response.sendRedirect("incident-list.jsp");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        if ("update".equals(action)) {
            updateIncident(request, response);
        } else if ("updateStatus".equals(action)) {
            updateStatus(request, response);
        } else {
            try {
                insertIncident(request, response);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private void updateStatus(HttpServletRequest request, HttpServletResponse response) throws IOException {
        int incidentId = Integer.parseInt(request.getParameter("incident_id"));
        String status = request.getParameter("status");
        incidentDAO.updateIncidentStatus(incidentId, status);
        response.sendRedirect("incident-list.jsp");
    }

    private void showNewForm(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        RequestDispatcher dispatcher = request.getRequestDispatcher("incident-form.jsp");
        dispatcher.forward(request, response);
    }

    // Method to delete an item from the database
    private void deleteIncident(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int incident_id = Integer.parseInt(request.getParameter("incident_id"));
        incidentDAO.deleteIncident(incident_id);
        response.sendRedirect("incident-list.jsp");
    }
}
