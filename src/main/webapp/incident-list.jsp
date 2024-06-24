<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.sql.*" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Incident List</title>
    <!-- Add Font Awesome CSS -->
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.4/css/all.min.css">
    <style>
        /* General styles */
        body {
            font-family: Arial, sans-serif;
            background-color: #f2f2f2;
            margin: 0;
            padding: 0;
        }

        /* Navigation bar styles */
        .navbar {
            overflow: hidden;
            background-color: #333;
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

        /* Main content area styles */
        .content {
            padding: 20px;
            margin-bottom: 70px; /* Adjust margin to accommodate footer */
        }

        /* Table styles */
        table {
            width: 100%;
            border-collapse: collapse;
            background-color: #fff;
            box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
        }

        th, td {
            border: 1px solid #ddd;
            padding: 8px;
            text-align: left;
        }

        th {
            background-color: #f2f2f2;
            font-weight: bold;
        }

        td {
            font-size: 14px;
        }

        .actions {
            display: flex;
            gap: 10px;
        }

        .actions a {
            color: #1E9FF2;
            text-decoration: none;
            padding: 5px 10px;
            border: 1px solid #ddd;
            border-radius: 4px;
            transition: background-color 0.3s, color 0.3s;
        }

        .actions a:hover {
            background-color: #0d8dc3;
            color: white;
        }

        /* Footer styles */
        footer {
            background-color: #333;
            color: white;
            text-align: center;
            padding: 10px;
            position: absolute;
            width: 100%;
            bottom: 0;
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
                        Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
                        con = DriverManager.getConnection("jdbc:sqlserver://localhost:1433;databaseName=cidrz_ict", "sa", "12345");
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
                        <a href='IncidentServlet?action=edit&incident_id=<%= rs.getLong("incident_id") %>'><i class='fas fa-edit'></i> Edit</a>
                        <a href='IncidentServlet?action=delete&incident_id=<%= rs.getLong("incident_id") %>'><i class='fas fa-trash-alt'></i> Delete</a>
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
</body>
</html>
