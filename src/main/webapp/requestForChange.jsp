<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.sql.*" %>
<%@ page import="com.andyprofinnovations.dao.DBConnection" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Incident List</title>
    <!-- Add Font Awesome CSS -->
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.4/css/all.min.css">
   <link rel="stylesheet"  href="${pageContext.request.contextPath}/css/list.css">
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
                    <th>Request No</th>
                    <th>Log Date</th>
                    <th>Causes</th>
                    <th>Change Date</th>
                    <th>Description of Change</th>
                    <th>Motivation</th>
                    <th>Additional Information</th>
                    <th>Department</th>
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
                        rs = stmt.executeQuery("SELECT * FROM requestForChange");

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
        <button a href="reguest-list.jsp">reguests</button>
    </div>

</body>
</html>

