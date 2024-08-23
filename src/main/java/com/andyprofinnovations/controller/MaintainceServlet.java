package com.andyprofinnovations.controller;

import com.andyprofinnovations.dao.MaintenanceDAO;
import com.andyprofinnovations.model.Maintenance;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

public class MaintainceServlet extends HttpServlet {

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
                    insertMaintaince(request, response);
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
                break;
            case "edit":
                showEditForm(request, response);
                break;
            case "update":
                updateMaintenanceForm(request, response);
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
                    listMaintaince(request, response);
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
                break;
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        if("insert".equals(action)){
            try {
                insertMaintaince(request, response);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }else if("update".equals(action)){
            updateMaintenanceForm(request, response);
        }
        }


    private void listMaintaince(HttpServletRequest request, HttpServletResponse response) throws IOException, SQLException {

        String list  = maintenanceDAO.listMaintance().toString();
        request.setAttribute("list", list);
        response.sendRedirect("list-maintenance");

    }

    private void deleteMaintenance(HttpServletRequest request, HttpServletResponse response) throws SQLException {
        int id = Integer.parseInt(request.getParameter("id"));
        maintenanceDAO.deleteMaintaince(id);

    }

    private void updateMaintenanceForm(HttpServletRequest request, HttpServletResponse response) {
        Maintenance maintaince = new Maintenance();
        maintaince.setGudgetName("gudgetName");
        maintaince.setSerialnumber(Integer.parseInt("serialnumber"));
        maintaince.setProblemDescription("problemDescription");
        maintaince.setStatus("status");
        maintaince.setStorageArea("storageArea");
        maintaince.setBroughtBy("broughtBy");
        maintaince.setReceivedBy("receivedBy");
        maintaince.setUpdateby("updateby");
        maintaince.setDateBrought("dateBrought");
        maintaince.setDateBrought("lastUpDated");
        maintenanceDAO.editMaintenance(maintaince);

    }

    private void showEditForm(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String id = String.valueOf(Integer.parseInt(request.getParameter("id")));
        Maintenance existingMaintenance = maintenanceDAO.findMaintenance(id);
        request.setAttribute("maintenance", existingMaintenance);
        RequestDispatcher dispatcher = request.getRequestDispatcher("Maintenance-edit.jsp");
        dispatcher.forward(request, response);


    }

    private void insertMaintaince(HttpServletRequest request, HttpServletResponse response) throws IOException, SQLException {
        String gudgetName = request.getParameter("gudgetName");
        String serialnumber = request.getParameter("serialnumber");
        String problemDescription = request.getParameter("problemDescription");
        String status = request.getParameter("status");
        String storageArea = request.getParameter("storageArea");
        String broughtBy = request.getParameter("broughtBy");;
        String receivedBy = request.getParameter("receivedBy");
        String updateby = request.getParameter("updateBy");;
        String dateBrought = request.getParameter("datebrought");
        String lastUpDated = request.getParameter("lastUpDated");

        Maintenance maintaince = new Maintenance();
      maintaince.setGudgetName(gudgetName);
      maintaince.setSerialnumber(Integer.parseInt(serialnumber));
      maintaince.setProblemDescription(problemDescription);
      maintaince.setStatus(status);
      maintaince.setStorageArea(storageArea);
      maintaince.setBroughtBy(broughtBy);
      maintaince.setReceivedBy(receivedBy);
      maintaince.setUpdateby(updateby);
      maintaince.setDateBrought(dateBrought);
      maintaince.setDateBrought(lastUpDated);
      maintenanceDAO.addGudget(maintaince);
      response.sendRedirect("list.jsp");

    }

    private void showNewForm(HttpServletRequest request, HttpServletResponse response) {
        request.getRequestDispatcher("maintenance-form.jsp");
    }
}
