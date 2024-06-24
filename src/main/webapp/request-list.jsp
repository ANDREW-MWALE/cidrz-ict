<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.sql.*" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Request For Change List</title>
    <!-- Add Font Awesome CSS -->
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.4/css/all.min.css">
    <style>
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

        /* Footer styles */
        footer {
            background-color: #333;
            color: white;
            text-align: center;
            padding: 10px;
            position: absolute; /* Position footer relative to the content */
            width: 100%;
            bottom: 0;
        }

        /* Table styles */
        table {
            width: 100%;
            border-collapse: collapse;
        }

        th, td {
            border: 1px solid #ddd;
            padding: 8px;
            text-align: left;
        }

        th {
            background-color: #f2f2f2;
        }

        .actions {
            display: flex;
            gap: 10px;
        }

        .actions a {
            color: #1E9FF2; /* Updated color */
            text-decoration: none;
            padding: 5px 10px;
            border: 1px solid #ddd;
            border-radius: 4px;
            transition: background-color 0.3s, color 0.3s;
        }

        .actions a:hover {
            background-color: #0d8dc3; /* Slightly darker shade for hover */
            color: white;
        }
    </style>
</head>
<body>
    <div class="navbar">
        <a href="home.jsp">Home</a>
        <a href="incident-form.jsp">Report Incident</a>
    </div>

    <div class="content">
        <h1>Requests </h1>

        <%
            Connection con = null;
            Statement stmt = null;
            ResultSet rs = null;
            try {
                Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
                con = DriverManager.getConnection("jdbc:sqlserver://localhost:1433;databaseName=cidrz_ict", "sa", "12345");
                stmt = con.createStatement();
                rs = stmt.executeQuery("SELECT * FROM requestForChange");

                out.println("<table>");
                out.println("<thead>");
                out.println("<tr>");
                out.println("<th>ID</th>");
                out.println("<th>Request No</th>");
                out.println("<th>Log Date</th>");
                out.println("<th>Change Date</th>");
                out.println("<th>Description Of Change</th>");
                out.println("<th>Motivation</th>");
                out.println("<th>Additional Information</th>");
                out.println("<th>Department</th>");
                out.println("<th>Actions</th>");
                out.println("</tr>");
                out.println("</thead>");
                out.println("<tbody>");

                while (rs.next()) {
                    out.println("<tr>");
                    out.println("<td>" + rs.getInt("id") + "</td>");
                    out.println("<td>" + rs.getString("requestNo") + "</td>");
                    out.println("<td>" + rs.getDate("logDate") + "</td>");
                    out.println("<td>" + rs.getDate("changeDate") + "</td>");
                    out.println("<td>" + rs.getString("descriptionOfChange") + "</td>");
                    out.println("<td>" + rs.getString("motivation") + "</td>");
                    out.println("<td>" + rs.getString("adInfo") + "</td>");
                    out.println("<td>" + rs.getString("department") + "</td>");
                    out.println("<td class='actions'>");
                    out.println("<a href='RequestForChangeServlet?action=edit&id=" + rs.getInt("id") + "'><i class='fas fa-edit'></i> Edit</a>");
                    out.println("<a href='RequestForChangeServlet?action=delete&id=" + rs.getInt("id") + "'><i class='fas fa-trash-alt'></i> Delete</a>");
                    out.println("</td>");
                    out.println("</tr>");
                }

                out.println("</tbody>");
                out.println("</table>");
            } catch (Exception e) {
                out.println("Error: " + e.getMessage());
            } finally {
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
    </div>
</body>
</html>
