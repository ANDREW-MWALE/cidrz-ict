package com.andyprofinnovations.controller;

import com.andyprofinnovations.dao.RequestForChangeDAO;
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
import java.util.List;

public class RequestForChangeServlet extends HttpServlet {
    private RequestForChangeDAO requestForChangeDAO;

    @Override
    public void init() {
        requestForChangeDAO = new RequestForChangeDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        if (action == null) {
            action = "";
        }

        try {
            switch (action) {
                case "new":
                    showNewForm(request, response);
                    break;
                case "insert":
                    insertRequestForChange(request, response);
                    break;
                case "edit":
                    showEditForm(request, response);
                    break;
                case "update":
                    updateRequestForChange(request, response);
                    break;
                case "delete":
                    deleteRequestForChange(request, response);
                    break;
                default:
                    listRequestForChange(request, response);
                    break;
            }
        } catch (SQLException e) {
            throw new ServletException(e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        if ("update".equals(action)) {
            try {
                updateRequestForChange(request, response);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        } else {
            try {
                insertRequestForChange(request, response);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private void listRequestForChange(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<RequestForChange> requestForChanges = requestForChangeDAO.listRequestForChange();
        request.setAttribute("requestForChanges", requestForChanges);
        RequestDispatcher dispatcher = request.getRequestDispatcher("request-list.jsp");
        dispatcher.forward(request, response);
    }

    private void deleteRequestForChange(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        requestForChangeDAO.deleteRequestForChange(id);
        response.sendRedirect("RequestForChangeServlet?action=list");
    }

    private void updateRequestForChange(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException {

        RequestForChange requestForChange = new RequestForChange();
        requestForChange.setId(Integer.parseInt(request.getParameter("id")));
        requestForChange.setRequestNo(request.getParameter("requestNo"));
        requestForChange.setLogDate(Date.valueOf(request.getParameter("logDate")));
        requestForChange.setChangeDate(Date.valueOf(request.getParameter("changeDate")));
        requestForChange.setDescriptionOfChange(request.getParameter("descriptionOfChange"));
        requestForChange.setMotivation(request.getParameter("motivation"));
        requestForChange.setAdInfo(request.getParameter("adInfo"));
        requestForChange.setDepartment(request.getParameter("Department"));
        requestForChange.setCreatedBy(request.getParameter("createdBy"));
        requestForChange.setDepartment(request.getParameter("updatedBy"));
        requestForChange.setChangeDate(Date.valueOf(request.getParameter("changeDate")));
        requestForChange.setLastUpdatedDate(Date.valueOf(request.getParameter("LastUpdatedDate")));
        requestForChange.setApprove(Integer.parseInt(request.getParameter("Approve")));

        requestForChangeDAO.editRequest(requestForChange);
        response.sendRedirect("RequestForChangeServlet?action=list");
    }

    private void showEditForm(HttpServletRequest request, HttpServletResponse response) throws SQLException, ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        RequestForChange existingRequest = requestForChangeDAO.findRequest(id);
        RequestDispatcher dispatcher = request.getRequestDispatcher("updateRequest.jsp");
        request.setAttribute("requestForChange", existingRequest);
        dispatcher.forward(request, response);
    }

    private void insertRequestForChange(HttpServletRequest request, HttpServletResponse response) throws SQLException, ServletException, IOException {
        String requestNo = request.getParameter("requestNo");
        Date logDate = Date.valueOf(request.getParameter("logDate"));
        Date changeDate = Date.valueOf(request.getParameter("changeDate"));
        String descriptionOfChange = request.getParameter("descriptionOfChange");
        String motivation = request.getParameter("motivation");
        String adInfo = request.getParameter("adInfo");
        String department = request.getParameter("Department");
        int approve = Integer.parseInt(request.getParameter("Approve"));

        HttpSession session = request.getSession();
        Users loggedInUser = (Users) session.getAttribute("loggedInUser");

        if (loggedInUser == null) {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "You must be logged in to submit a request.");
            return;
        }

        String createdBy = String.valueOf(loggedInUser.getUser_id());
        String updatedBy = createdBy;
        Date currentTimestamp = new Date(System.currentTimeMillis());

        RequestForChange requestForChange = new RequestForChange();
        requestForChange.setRequestNo(requestNo);
        requestForChange.setLogDate(logDate);
        requestForChange.setChangeDate(changeDate);
        requestForChange.setDescriptionOfChange(descriptionOfChange);
        requestForChange.setMotivation(motivation);
        requestForChange.setAdInfo(adInfo);
        requestForChange.setDepartment(department);
        requestForChange.setCreatedBy(createdBy);
        requestForChange.setUpdatedBy(updatedBy);
        requestForChange.setCreatedDate(currentTimestamp);
        requestForChange.setLastUpdatedDate(currentTimestamp);
        requestForChange.setApprove(approve);

        requestForChangeDAO.addRequest(requestForChange);
        response.sendRedirect("RequestForChangeServlet?action=list");
    }

    private void showNewForm(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        RequestDispatcher dispatcher = request.getRequestDispatcher("requestForChange-form.jsp");
        dispatcher.forward(request, response);
    }
}
