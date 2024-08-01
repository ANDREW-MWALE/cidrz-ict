package com.andyprofinnovations.controller;

import com.andyprofinnovations.dao.RequestForChangeDAO;
import com.andyprofinnovations.model.Incident;
import com.andyprofinnovations.model.RequestForChange;
import com.andyprofinnovations.model.Users;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;

public class RequestForChangeServlet extends HttpServlet {
    private RequestForChangeDAO requestForChangeDAO;

    @Override
    public void init(){
        requestForChangeDAO = new RequestForChangeDAO();
    }
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String action = request.getParameter("action");

        if(action==null){
          action = "";
        }
        switch (action){
            case "new":
                showNewForm(request,response);
                break;
            case "insert":
                try {
                    insertRequestForChange(request,response);
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
                    updateRequestForChange(request, response);
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
                break;
            case "delete":
                try {
                    deleteRequestForChange(request, response);
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
                break;
            default:
             listRequestForChange(request, response);
             break;
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        try {
            if ("update".equals("action")) {  // Check if the action is to update
                updateRequestForChange(request, response);  // Call the method to update the request
            }else {
                insertRequestForChange(request,response);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    private void listRequestForChange(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
        List<RequestForChange> requestForChanges = requestForChangeDAO.listRequestForChange();
        request.setAttribute("requestForChanges", requestForChanges);
        request.getRequestDispatcher("request-list.jsp").forward(request, response);

    }

    private void deleteRequestForChange(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException {
        requestForChangeDAO.deleteRequestForChange(Integer.valueOf(request.getParameter("id")));
        response.sendRedirect("?action=list");

    }

    private void updateRequestForChange(HttpServletRequest request, HttpServletResponse response) throws SQLException {
        RequestForChange requestForChange = new RequestForChange();

        requestForChange.setRequestNo(request.getParameter("requestNo"));
        requestForChange.setLogDate(Date.valueOf(request.getParameter("logDate")));
        requestForChange.setChangeDate(Date.valueOf(request.getParameter("changeDate")));
        requestForChange.setDescriptionOfChange(request.getParameter("descriptionOfChange"));
        requestForChange.setMotivation(request.getParameter("adInfo"));
        requestForChange.setDepartment(request.getParameter("Department"));
        requestForChange.setApprove(Integer.parseInt(request.getParameter("Approve")));

        requestForChangeDAO.editRequest(requestForChange);

    }

    private void showEditForm(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException {
     int Id = Integer.parseInt(request.getParameter("id"));
     request.getRequestDispatcher("showEditForm" + Id);
     RequestForChange existingRequests = requestForChangeDAO.findRequest(Id);
     request.setAttribute("requestForChange", existingRequests);

     RequestDispatcher dispatcher = request.getRequestDispatcher("updateRequest.jsp");
     dispatcher.forward(request, response);


    }


    private void insertRequestForChange(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException {
        String requestNo = request.getParameter("requestNo");
        String logDate = request.getParameter("logDate");
        String changeDate = request.getParameter("changeDate");
        String descriptionOfChange = request.getParameter("descriptionOfChange");
        String motivation = request.getParameter("motivation");
        String adInfo = request.getParameter("adInfo");
        String department = request.getParameter("Department");
        String approveStr = request.getParameter("Approve");

        HttpSession session = request.getSession();
        Users loggedInUser = (Users) session.getAttribute("loggedInUser");

        if (loggedInUser == null) {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "You must be logged in to submit an incident.");
            return;
        }

        String createdBy = String.valueOf(loggedInUser.getUser_id());
        String updatedBy = createdBy; // Assuming the incident is created and updated by the same user initially

        Date currentTimestamp = new Date(System.currentTimeMillis());

        RequestForChange requestForChange = new RequestForChange();
        requestForChange.setRequestNo(requestNo);
        requestForChange.setLogDate(Date.valueOf(logDate));
        requestForChange.setChangeDate(Date.valueOf(changeDate));
        requestForChange.setDescriptionOfChange(descriptionOfChange);
        requestForChange.setMotivation(motivation);
        requestForChange.setAdInfo(adInfo);
        requestForChange.setDepartment(department);
        requestForChange.setCreatedBy(createdBy);
        requestForChange.setUpdatedBy(updatedBy);
        requestForChange.setCreatedDate(currentTimestamp);
        requestForChange.setLastUpdatedDate(currentTimestamp);

        if (approveStr != null && !approveStr.isEmpty()) {
            try {
                int approve = Integer.parseInt(approveStr);
                requestForChange.setApprove(approve);
            } catch (NumberFormatException e) {
                requestForChange.setApprove(0); // Default value in case of parsing error
                e.printStackTrace(); // Optional: Log the stack trace for debugging
            }
        } else {
            requestForChange.setApprove(0); // Default value if not provided
        }

        requestForChangeDAO.AddRequest(requestForChange);

        response.sendRedirect("request-list.jsp");
    }


    private void showNewForm(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
           RequestDispatcher dispatcher = request.getRequestDispatcher("requestForChange-form.jsp");
           dispatcher.forward(request,response);
    }

}
