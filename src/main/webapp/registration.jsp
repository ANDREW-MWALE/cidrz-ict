<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page import="java.sql.Connection, java.sql.DriverManager, java.sql.Statement, java.sql.ResultSet, java.sql.SQLException" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>User Registration</title>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.4/css/all.min.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/registration.css">
    <style>

    </style>
</head>
<body>
    <div class="container">
        <img src="assets/CIDRZ-logo_HR_RGB.jpg" alt="Logo" width="400" height="80">
        <h2>User Registration</h2>
        <%
            String errorMessage = (String) request.getAttribute("errorMessage");
            if (errorMessage != null) {
        %>
        <div class="error"><%= errorMessage %></div>
        <% } %>
        <form action="UserServlet" method="post">
            <input type="hidden" name="action" value="register">
            <label for="name">Name:</label><br>
            <div class="input-group">
                <i class="fa fa-user"></i>
                <input type="text" id="name" name="name" required>
            </div>
            <label for="position">Position:</label><br>
            <div class="input-group">
                <i class="fa fa-briefcase"></i>
                <input type="text" id="position" name="position" required>
            </div>
            <label for="email">Email:</label><br>
            <div class="input-group">
                <i class="fa fa-envelope"></i>
                <input type="email" id="email" name="email" required>
            </div>
            <label for="role_id">Role ID:</label>
            <div class="input-group">
                <i class="fa fa-user-tag"></i>
                <select id="role_id" name="role_id" class="form-control" required>
                    <option value="">Choose your role</option>
                    <%
                        Connection con = null;
                        Statement st = null;
                        ResultSet rs = null;
                        try {
                            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
                            con = DriverManager.getConnection("jdbc:sqlserver://localhost:1433;databaseName=cidrz_ict", "sa", "12345");
                            st = con.createStatement();
                            String query = "SELECT * FROM role"; // Ensure this matches your roles table
                            rs = st.executeQuery(query);
                            while (rs.next()) {
                                int roleId = rs.getInt("role_id");
                                String roleName = rs.getString("name");
                    %>
                    <option value="<%= roleId %>"><%= roleName %></option>
                    <%
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        } finally {
                            try {
                                if (rs != null) rs.close();
                                if (st != null) st.close();
                                if (con != null) con.close();
                            } catch (SQLException e) {
                                e.printStackTrace();
                            }
                        }
                    %>
                </select>
            </div>
            <label for="password">Password:</label><br>
            <div class="input-group">
                <i class="fa fa-lock"></i>
                <input type="password" id="password" name="password" required>
            </div>
            <label for="confirm_password">Confirm Password:</label><br>
            <div class="input-group">
                <i class="fa fa-lock"></i>
                <input type="password" id="confirm_password" name="confirm_password" required>
            </div>
            <input type="submit" value="Register"><br>
            <p>Already have an account? <a href="index.jsp">Login</a></p>
        </form>
    </div>
</body>
</html>
