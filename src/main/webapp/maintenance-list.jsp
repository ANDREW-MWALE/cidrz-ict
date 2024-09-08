<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List" %>
<%@ page import="com.andyprofinnovations.model.Maintenance" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Maintenance List</title>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.4/css/all.min.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/request_list_style.css">
</head>
<body>
    <div class="navbar">
        <a href="home.jsp">Home</a>
        <a href="incident-form.jsp">Report Incident</a>
    </div>

    <div class="content">
        <h1>Maintenance Requests</h1>

        <table>
            <thead>
                <tr>
                    <th>ID</th>
                    <th>Gadget Name</th>
                    <th>Serial Number</th>
                    <th>Problem Description</th>
                    <th>Status</th>
                    <th>Storage Area</th>
                    <th>Brought By</th>
                    <th>Received By</th>
                    <th>Updated By</th>
                    <th>Date Brought</th>
                    <th>Last Updated</th>
                    <th>Actions</th>
                </tr>
            </thead>
            <tbody>
                <%
                    List<com.andyprofinnovations.model.Maintenance> maintenanceList =
                        (List<com.andyprofinnovations.model.Maintenance>) request.getAttribute("maintenanceList");
                    if (maintenanceList != null) {
                        for (com.andyprofinnovations.model.Maintenance maintenance : maintenanceList) {
                %>
                    <tr>
                        <td><%= maintenance.getId() %></td>
                        <td><%= maintenance.getGudgetName() %></td>
                        <td><%= maintenance.getSerialnumber() %></td>
                        <td><%= maintenance.getProblemDescription() %></td>
                        <td><%= maintenance.getStatus() %></td>
                        <td><%= maintenance.getStorageArea() %></td>
                        <td><%= maintenance.getBroughtBy() %></td>
                        <td><%= maintenance.getReceivedBy() %></td>
                        <td><%= maintenance.getUpdateby() %></td>
                        <td><%= maintenance.getDateBrought() %></td>
                        <td><%= maintenance.getLastUpDated() %></td>
                        <td class="actions">
                            <a href="MaintainceServlet?action=edit&id=<%= maintenance.getId() %>">
                                <i class="fas fa-edit"></i> Edit
                            </a>
                            <a href="MaintainceServlet?action=delete&id=<%= maintenance.getId() %>">
                                <i class="fas fa-trash-alt"></i> Delete
                            </a>
                        </td>
                    </tr>
                <%
                        }
                    }
                %>
            </tbody>
        </table>
    </div>
</body>
</html>
