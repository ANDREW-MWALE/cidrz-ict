package com.andyprofinnovations.controller;

import com.andyprofinnovations.dao.IncidentDAO;
import com.andyprofinnovations.dao.LocationDAO;
import com.andyprofinnovations.model.Incident;
import com.andyprofinnovations.model.Location;
import com.andyprofinnovations.model.Users;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
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
        HttpSession session = request.getSession(false);
        if(session==   null||request.getAttribute("loggedUser")==null){
            response.sendRedirect("login.jsp");
            return;
        }
        Users loggedUser =(Users) session.getAttribute("loggedInUser");
        if(loggedUser.getRole_id()!=1){
            response.sendRedirect("error.jsp");
            return;
        }


            List<Incident> incidents = incidentDAO.listIncident();
                List<Location> locations = locationDAO.listLocation();

                // Set attributes in request scope
                request.setAttribute("incidents", incidents);
                request.setAttribute("locations", locations);

                // Forward to JSP for rendering
                request.getRequestDispatcher("updateIncident.jsp").forward(request, response);
            } catch (Exception e) {
                // Handle exceptions
                System.out.println("Error fetching data: " + e.getMessage());
                e.printStackTrace();
            }
        }

}





