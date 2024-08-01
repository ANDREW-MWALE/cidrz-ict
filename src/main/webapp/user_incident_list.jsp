<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.sql.*" %>
<%@ page import="com.andyprofinnovations.dao.DBConnection" %>
<%@ page import="com.andyprofinnovations.model.Users" %>

<%
    // Check if the user is logged in and has the correct role
    Users loggedInUser = (Users) session.getAttribute("loggedInUser");
    if (loggedInUser == null || !(loggedInUser.getRole_id() == 1 || loggedInUser.getRole_id() == 2)) {
        response.sendRedirect("error.jsp");
        return;
    }
%>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Incident List</title>
    <!-- Add Font Awesome CSS -->
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.4/css/all.min.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/list.css">
    <style>
        body {
            font-family: Arial, sans-serif;
            margin: 0;
            padding: 0;
            background-color: #f4f4f4;
        }
        .navbar {
            background-color: #333;
            overflow: hidden;
        }
        .navbar a {
            float: left;
            display: block;
            color: #f2f2f2;
            text-align: center;
            padding: 14px 20px;
            text-decoration: none;
        }
        .navbar a:hover {
            background-color: #ddd;
            color: black;
        }
        .content {
            margin: 20px;
            padding: 20px;
            background-color: #fff;
            box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
        }
        .report-buttons {
            margin-bottom: 20px;
        }
        .report-buttons button {
            margin-right: 10px;
            padding: 10px 20px;
            font-size: 16px;
            cursor: pointer;
            background-color: #007bff;
            color: #fff;
            border: none;
            border-radius: 5px;
        }
        .report-buttons button:hover {
            background-color: #0056b3;
        }
        table {
            width: 100%;
            border-collapse: collapse;
        }
        table, th, td {
            border: 1px solid #ddd;
        }
        th, td {
            padding: 12px;
            text-align: left;
        }
        th {
            background-color: #f2f2f2;
        }
        .actions a {
            margin-right: 10px;
            color: #007bff;
            text-decoration: none;
        }
        .actions a:hover {
            color: #0056b3;
        }
        .actions a .fas {
            margin-right: 5px;
        }
    </style>
</head>
<body>
    <!-- Navigation bar -->
    <div class="navbar">
        <a href="home.jsp">Home</a>
        <a href="incident-form.jsp">Report Incident</a>
    </div>

    <div class="content">
        <h1>Incidents</h1>
        <div class="report-buttons">
            <button onclick="generateReport('weekly')">Generate Weekly Report</button>
            <button onclick="generateReport('monthly')">Generate Monthly Report</button>
            <button onclick="generateReport('annual')">Generate Annual Report</button>
            <p class="beta-disclaimer">* This feature is in beta testing</p>
        </div>
        <table>
            <thead>
                <tr>
                    <th>Incident ID</th>
                    <th>Name</th>
                    <th>Description</th>
                    <th>Causes</th>
                    <th>Location ID</th>
                    <th>Created By</th>
                    <th>Created Date</th>
                    <th>Updated By</th>
                    <th>Last Updated Date</th>
                    <th>Actions</th>
                </tr>
            </thead>
            <tbody>
                <% // Java code to fetch incidents from database and display them
                    Connection con = null;
                    Statement stmt = null;
                    ResultSet rs = null;
                    try {
                        con = DBConnection.getConn();
                        stmt = con.createStatement();
                        rs = stmt.executeQuery("SELECT * FROM incident");

                        while (rs.next()) {
                %>
                <tr>
                    <td><%= rs.getLong("incident_id") %></td>
                    <td><%= rs.getString("name") %></td>
                    <td><%= rs.getString("description") %></td>
                    <td><%= rs.getString("causes") %></td>
                    <td><%= rs.getInt("location_id") %></td>
                    <td><%= rs.getString("created_by") %></td>
                    <td><%= rs.getDate("created_date") %></td>
                    <td><%= rs.getString("updated_by") %></td>
                    <td><%= rs.getDate("last_updated_date") %></td>
                    <td class='actions'>
                        <a href='updateIncident.jsp?action=edit&incident_id=<%= rs.getLong("incident_id") %>'><i class='fas fa-edit'></i> Edit</a>
                        <a href='IncidentServlet?action=delete&incident_id=<%= rs.getLong("incident_id") %>'> Cancel</a>
                    </td>
                </tr>
                <%
                        }
                    } catch (Exception e) {
                        out.println("Error: " + e.getMessage());
                    } finally {
                        // Close resources
                        if (rs != null) {
                            try {
                                rs.close();
                            } catch (SQLException e) {
                                e.printStackTrace();
                            }
                        }
                        if (stmt != null) {
                            try {
                                stmt.close();
                            } catch (SQLException e) {
                                e.printStackTrace();
                            }
                        }
                        if (con != null) {
                            try {
                                con.close();
                            } catch (SQLException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                %>
            </tbody>
        </table>
    </div>
    <script>
        function generateReport(period) {
            window.location.href = `GenerateReportServlet?period=${period}`;
        }
    </script>
</body>
</html>
