package com.andyprofinnovations.controller;

import com.andyprofinnovations.dao.IncidentDAO;
import com.andyprofinnovations.dao.LocationDAO;
import com.andyprofinnovations.model.Incident;
import com.andyprofinnovations.model.Location;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class CreateIncidentServlet extends HttpServlet {
    private IncidentDAO incidentDAO;
    private LocationDAO locationDAO;

    @Override
    public void init() throws ServletException {
        incidentDAO = new IncidentDAO();
        locationDAO = new LocationDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
                // Fetch list of incidents and locations from DAOs
                List<Incident> incidents = incidentDAO.listIncident();
                List<Location> locations = locationDAO.listLocation();

                // Set attributes in request scope
                request.setAttribute("incidents", incidents);
                request.setAttribute("locations", locations);

                // Forward to JSP for rendering
                request.getRequestDispatcher("incident-form.jsp").forward(request, response);
            } catch (Exception e) {
                // Handle exceptions
                System.out.println("Error fetching data: " + e.getMessage());
                e.printStackTrace();
            }
        }

}





