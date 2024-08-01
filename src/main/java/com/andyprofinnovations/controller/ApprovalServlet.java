package com.andyprofinnovations.controller;

import com.andyprofinnovations.dao.ApprovalDAO;
import com.andyprofinnovations.model.Approval;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class ApprovalServlet extends HttpServlet {
    private ApprovalDAO approvalDAO;

    @Override
    public void init() {
       approvalDAO=new ApprovalDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");

        if(action==null){
            action = "";
        }


        switch (action){
            case "new":
                showApprovalForm(request, response);
                break;
            case "insert":
                try {
                    insertApprovals(request, response);
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
                break;
            case "edit":
                try {
                    EditApproval(request, response);
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
                break;
            case "update":
                try {
                    updateApprovals(request, response);
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            case "delete":
                try {
                    deleteApproval(request, response);
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
                break;
            default:
                try {
                    listApprovals(request, response);
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
                break;
        }

    }

    private void listApprovals(HttpServletRequest request, HttpServletResponse response) throws SQLException {
        List<Approval> approvals = approvalDAO.listApprovals();
        request.setAttribute("approvals", approvals);

    }

    private void deleteApproval(HttpServletRequest request, HttpServletResponse response) throws IOException, SQLException {
        approvalDAO.deleteApproval(request.getParameter("Aid"));
        response.sendRedirect("approval-list.jsp");

    }

    private void updateApprovals(HttpServletRequest request, HttpServletResponse response) throws SQLException {
        Approval approval = new Approval();
        approval.setAid(Integer.parseInt(request.getParameter("Aid")));
        approval.setName(request.getParameter("name"));

        approvalDAO.editApproval(approval);

    }

    private void EditApproval(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException {
        int ApprovalAid = Integer.parseInt(request.getParameter("Aid"));
        request.getRequestDispatcher("showEditForm" + ApprovalAid
        );
        Approval existingApproval = approvalDAO.findApproval(ApprovalAid);
        request.setAttribute("approvals", existingApproval);
        RequestDispatcher dispatcher = request.getRequestDispatcher("approval-list.jsp");
        dispatcher.forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        try {
            insertApprovals(request, response);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    private void insertApprovals(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException {
        String name = request.getParameter("name");
        Approval approval = new Approval();
        approval.setName(name);
        approvalDAO.addApproval(approval);

       response.sendRedirect("home.jsp");

    }

    private void showApprovalForm(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
      RequestDispatcher dispatcher = request.getRequestDispatcher("approval-form");
      dispatcher.forward(request, response);
    }



}
