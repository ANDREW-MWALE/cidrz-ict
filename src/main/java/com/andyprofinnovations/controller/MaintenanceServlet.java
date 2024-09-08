package com.andyprofinnovations.controller;

import com.andyprofinnovations.dao.MaintenanceDAO;
import com.andyprofinnovations.model.Maintenance;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;
import java.util.List;

public class MaintenanceServlet extends HttpServlet {

    private MaintenanceDAO maintenanceDAO;

    @Override
    public void init(){
        maintenanceDAO = new MaintenanceDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
       String action = request.getParameter("action");

       if(action==null){
           action="";
       }
        switch(action){
            case "new":
                showNewForm(request, response);
                break;
            case "insert":
                try {
                    insertMaintenance(request, response);
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
                break;
            case "edit":
                try {
                    showEditForm(request, response);
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
                break;
            case "update":
                try {
                    updateMaintenanceForm(request, response);
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
                break;
            case "delete":
                try {
                    deleteMaintenance(request, response);
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
                break;
            default:
                try {
                    listMaintenance(request, response);
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
                break;
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("here for example");
        String action = request.getParameter("action");
        if("insert".equals(action)){

            try {
                insertMaintenance(request, response);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }

        }else if("update".equals(action)){
            try {
                updateMaintenanceForm(request, response);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        }


    private void listMaintenance(HttpServletRequest request, HttpServletResponse response) throws IOException, SQLException, ServletException {
        List<Maintenance> maintenanceList = maintenanceDAO.listMaintenance(); // or however you fetch the data
        request.setAttribute("maintenanceList", maintenanceList);
        RequestDispatcher dispatcher = request.getRequestDispatcher("maintenance-list.jsp");
        dispatcher.forward(request, response);
    }

    private void deleteMaintenance(HttpServletRequest request, HttpServletResponse response) throws SQLException {
        int id = Integer.parseInt(request.getParameter("id"));
        maintenanceDAO.deleteMaintaince(id);

    }

    private void updateMaintenanceForm(HttpServletRequest request, HttpServletResponse response) throws SQLException {
        Maintenance maintenance = new Maintenance();
        maintenance.setGudgetName("gudgetName");
        maintenance.setSerialnumber(Integer.parseInt("serialnumber"));
        maintenance.setProblemDescription("problemDescription");
        maintenance.setStatus("status");
        maintenance.setStorageArea("storageArea");
        maintenance.setBroughtBy("broughtBy");
        maintenance.setReceivedBy("receivedBy");
        maintenance.setUpdateby("updateby");
        maintenance.setDateBrought(Date.valueOf("dateBrought"));
        maintenance.setDateBrought(Date.valueOf("lastUpDated"));
        maintenanceDAO.editMaintenance(maintenance);

    }

    private void showEditForm(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException {
        String id = String.valueOf(Integer.parseInt(request.getParameter("id")));
        Maintenance existingMaintenance = maintenanceDAO.findMaintenance(Integer.parseInt(id));
        request.setAttribute("maintenance", existingMaintenance);
        RequestDispatcher dispatcher = request.getRequestDispatcher("Maintenance-edit.jsp");
        dispatcher.forward(request, response);


    }

    private void insertMaintenance(HttpServletRequest request, HttpServletResponse response) throws IOException, SQLException {
        System.out.println("and here");
        String gudgetName = request.getParameter("gudgetName");
        String serialnumber = request.getParameter("serialnumber");
        String problemDescription = request.getParameter("problemDescription");
        String status = request.getParameter("status");
        String storageArea = request.getParameter("storageArea");
        String broughtBy = request.getParameter("broughtBy");
        String receivedBy = request.getParameter("receivedBy");
        String updateby = request.getParameter("updateby");
        String dateBrought = request.getParameter("dateBrought");
        String lastUpDated = request.getParameter("lastUpDated");

        try {
            Maintenance maintenance = new Maintenance();
            maintenance.setGudgetName(gudgetName);
            maintenance.setSerialnumber(Integer.parseInt(serialnumber));
            maintenance.setProblemDescription(problemDescription);
            maintenance.setStatus(status);
            maintenance.setStorageArea(storageArea);
            maintenance.setBroughtBy(broughtBy);
            maintenance.setReceivedBy(receivedBy);
            maintenance.setUpdateby(updateby);

            // Convert strings to java.sql.Date
            maintenance.setDateBrought(Date.valueOf(dateBrought));
            maintenance.setLastUpDated(Date.valueOf(lastUpDated)); // Note: Use setLastUpdated for the correct field

            maintenanceDAO.addGudget(maintenance);
            response.sendRedirect("maintenance-list.jsp");
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
            response.getWriter().println("Invalid date format. Expected yyyy-MM-dd.");
        }
    }

    private void showNewForm(HttpServletRequest request, HttpServletResponse response) {
        request.getRequestDispatcher("maintenance-form.jsp");
    }
}
