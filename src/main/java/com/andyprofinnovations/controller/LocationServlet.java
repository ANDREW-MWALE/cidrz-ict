package com.andyprofinnovations.controller;

import com.andyprofinnovations.dao.LocationDAO;
import com.andyprofinnovations.model.Location;
import com.andyprofinnovations.model.Role;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class LocationServlet extends HttpServlet {

   private LocationDAO locationDAO;

  @Override
  public void init(){
       locationDAO = new LocationDAO();

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
                        insertLocation(request, response);
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }
                    break;
                case "edit":
                    showEditForm(request, response);
                    break;
                case "update":
                    updateLocation(request, response);
                    break;
                case "delete":
                    deleteLocation(request, response);
                    break;
                default:
                    try {
                        locationList(request,response);
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }

            }



        }
    }

    private void updateLocation(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
      Location location = new Location();
        location.setName(request.getParameter("name"));
        location.setLocation_id(Integer.parseInt(request.getParameter("location_id")));
        location.setAddress(request.getParameter("address"));
        try {
            locationDAO.editLocation(location);
        } catch (Exception ex) {
            throw  new RuntimeException("role not not found");
        }
        response.sendRedirect("?action=list");
    }
    private void locationList(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, SQLException {

    }

    private void deleteLocation(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        locationDAO.deleteLocation(Integer.valueOf(request.getParameter("location_id")));
        response.sendRedirect("location-list.jsp");
    }

    private void showEditForm(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Integer locationID = Integer.valueOf(request.getParameter("location_id"));
        System.out.println("showEditForm" + locationID);
        Location existingLocation = locationDAO.findLocation(String.valueOf(locationDAO));
        request.setAttribute("location", existingLocation);
        RequestDispatcher dispatcher = request.getRequestDispatcher("location-form.jsp");
        dispatcher.forward(request, response);


    }

    private void insertLocation(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException {
        String name = request.getParameter("name");
        String address = request.getParameter("address");

        Location location = new Location();
        location.setName(name);
        location.setAddress(address);

        locationDAO.addLocation(location);


    }

    private void showNewForm(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("location-form.jsp");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String name = request.getParameter("name");
        String address = request.getParameter("address");


        // Create new Incident object
        Location location = new Location();
        location.setName(name);
        location.setAddress(address);

        try {
            locationDAO.addLocation(location);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        response.sendRedirect("home.jsp");

    }
}
