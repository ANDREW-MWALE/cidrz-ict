package com.andyprofinnovations.controller;

import com.andyprofinnovations.dao.RequestForChangeDAO;
import com.andyprofinnovations.model.Incident;
import com.andyprofinnovations.model.RequestForChange;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;
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
                showEditForm(request, response);
                break;
            case "update":
                updateRequestForChange(request, response);
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
            insertRequestForChange(request, response);
        } catch (SQLException e) {
            throw new ServletException(e);
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

    private void updateRequestForChange(HttpServletRequest request, HttpServletResponse response) {
    }

    private void showEditForm(HttpServletRequest request, HttpServletResponse response) {
    }


    private void insertRequestForChange(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException {
          String requestNo = request.getParameter("requestNo");
          String LogDate = request.getParameter("logDate");
          String changeDate = request.getParameter("changeDate");
          String descriptionOfChange = request.getParameter("descriptionOfChange");
          String motivation = request.getParameter("motivation");
          String adInfo = request.getParameter("adInfo");
          String Department = request.getParameter("Department");

        RequestForChange requestForChange = new RequestForChange();
        requestForChange.setRequestNo(requestNo);
        requestForChange.setLogDate(Date.valueOf(LogDate));
        requestForChange.setChangeDate(Date.valueOf(changeDate));
        requestForChange.setDescriptionOfChange(descriptionOfChange);
        requestForChange.setMotivation(motivation);
        requestForChange.setAdInfo(adInfo);
        requestForChange.setDepartment(Department);

        requestForChangeDAO.AddRequest(requestForChange);

        response.sendRedirect("request-list.jsp");
    }

    private void showNewForm(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
           RequestDispatcher dispatcher = request.getRequestDispatcher("requestForChange-form.jsp");
           dispatcher.forward(request,response);
    }

}
