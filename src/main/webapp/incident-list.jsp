<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.sql.*" %>
<%@ page import="com.andyprofinnovations.dao.DBConnection" %>
<%@ page import="com.andyprofinnovations.model.Users" %>

<%
    // Check if the user is logged in
    Users loggedInUser = (Users) session.getAttribute("loggedInUser");
    if (loggedInUser == null) {
        response.sendRedirect("login.jsp");
        return;
    }

    // Get the logged-in user's ID and role
    int userId = loggedInUser.getUser_id();
    int userRole = loggedInUser.getRole_id();
%>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Incident List</title>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.4/css/all.min.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/list.css">
        <style>
            /* Style for the report buttons */
            .report-buttons form {
                display: inline-block;
                margin-right: 10px;
            }

            .report-buttons button {
                padding: 10px 20px;
                background-color: #007bff; /* Blue background */
                color: #fff; /* White text */
                border: none;
                border-radius: 5px; /* Rounded corners */
                cursor: pointer;
                font-size: 14px;
                transition: background-color 0.3s ease, transform 0.2s ease;
            }

            .report-buttons button:hover {
                background-color: #0056b3; /* Darker blue on hover */
                transform: scale(1.05); /* Slightly enlarge on hover */
            }

            .report-buttons button:active {
                background-color: #004494; /* Even darker blue when pressed */
                transform: scale(1); /* Reset scale when pressed */
            }
               .actions form select {
                        padding: 8px 12px;
                        font-size: 14px;
                        border: 1px solid #ccc;
                        border-radius: 5px;
                        background-color: #f8f9fa; /* Light background color */
                        color: #333; /* Dark text color */
                        cursor: pointer;
                        transition: border-color 0.3s ease, box-shadow 0.3s ease;
                    }

                    .actions form select:focus {
                        border-color: #007bff; /* Blue border on focus */
                        box-shadow: 0 0 5px rgba(0, 123, 255, 0.5); /* Blue shadow on focus */
                        outline: none; /* Remove default outline */
                    }

                    .actions form {
                        display: inline-block;
                        margin: 0;
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
        <% if (userRole == 1) { %> <!-- Only show report options for admins -->
        <div class="report-buttons">
            <!-- Forms for generating reports -->
            <form action="GenerateReportServlet" method="get">
                <input type="hidden" name="period" value="daily" />
                <button type="submit">Daily Report</button>
            </form>
            <form action="GenerateReportServlet" method="get">
                <input type="hidden" name="period" value="weekly" />
                <button type="submit">Weekly Report</button>
            </form>
            <form action="GenerateReportServlet" method="get">
                <input type="hidden" name="period" value="monthly" />
                <button type="submit">Monthly Report</button>
            </form>
            <form action="GenerateReportServlet" method="get">
                <input type="hidden" name="period" value="quarterly" />
                <button type="submit">Quarterly Report</button>
            </form>
            <form action="GenerateReportServlet" method="get">
                <input type="hidden" name="period" value="yearly" />
                <button type="submit">Yearly Report</button>
            </form>
        </div>
        <% } %> <!-- End of admin check -->

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
                <%
                    Connection con = null;
                    PreparedStatement pstmt = null;
                    ResultSet rs = null;
                    try {
                        con = DBConnection.getConn();
                        String query;

                        // Admin role (assuming role_id 1 is admin)
                        if (userRole == 1) {
                            query = "SELECT * FROM incident";
                            pstmt = con.prepareStatement(query);
                        } else {
                            // Regular user (non-admin)
                            query = "SELECT * FROM incident WHERE created_by = ?";
                            pstmt = con.prepareStatement(query);
                            pstmt.setInt(1, userId);
                        }

                        rs = pstmt.executeQuery();

                        while (rs.next()) {
                            String currentStatusId = rs.getString("status");
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
                        <% if (userRole == 1) { %> <!-- Show dropdown only for admin -->
                            <form action="IncidentServlet?action=updateStatus" method="post" style="display:inline;">
                                <input type="hidden" name="incident_id" value="<%= rs.getLong("incident_id") %>">
                                <select name="status" onchange="this.form.submit()">
                                    <option value=''>Select status</option>
                                    <%
                                        Connection con2 = null;
                                        Statement st = null;
                                        ResultSet rs2 = null;
                                        try {
                                            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
                                            con2 = DriverManager.getConnection("jdbc:sqlserver://localhost:1433;databaseName=cidrz_ict", "sa", "12345");
                                            st = con2.createStatement();
                                            String query2 = "SELECT * FROM status";
                                            rs2 = st.executeQuery(query2);
                                            while (rs2.next()) {
                                                int statusId = rs2.getInt("id");
                                                String statusName = rs2.getString("name");
                                                String selected = String.valueOf(statusId).equals(currentStatusId) ? "selected" : "";
                                    %>
                                                <option value='<%= statusId %>' <%= selected %>><%= statusName %></option>
                                                <%
                                            }
                                        } catch (Exception e) {
                                            e.printStackTrace();
                                        } finally {
                                            if (rs2 != null) rs2.close();
                                            if (st != null) st.close();
                                            if (con2 != null) con2.close();
                                        }
                                    %>
                                </select>
                            </form>
                        <% } else { %>
                            <!-- Show selected status as text for regular users -->
                          <%
                              Connection con3 = null;
                              PreparedStatement pstmt3 = null;
                              ResultSet rs3 = null;
                              try {
                                  con3 = DBConnection.getConn();
                                  String query3 = "SELECT name FROM status WHERE id = ?";
                                  pstmt3 = con3.prepareStatement(query3);
                                  pstmt3.setString(1, currentStatusId);  // Use setString() for varchar type
                                  rs3 = pstmt3.executeQuery();
                                  if (rs3.next()) {
                                      String statusName = rs3.getString("name");
                          %>
                          <p>Status: <strong><%= statusName %></strong></p>
                          <%
                                  }
                              } catch (Exception e) {
                                  e.printStackTrace();
                              } finally {
                                  if (rs3 != null) rs3.close();
                                  if (pstmt3 != null) pstmt3.close();
                                  if (con3 != null) con3.close();
                              }
                          %>
                        <% } %>
                    </td>
                </tr>
                <%
                        }
                    } catch (Exception e) {
                        out.println("Error: " + e.getMessage());
                    } finally {
                        // Close resources
                        if (rs != null) rs.close();
                        if (pstmt != null) pstmt.close();
                        if (con != null) con.close();
                    }
                %>
            </tbody>
        </table>
    </div>
</body>
</html>
