<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page import="java.sql.Connection, java.sql.DriverManager, java.sql.Statement, java.sql.ResultSet, java.sql.SQLException" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>User Registration</title>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.4/css/all.min.css">
    <style>
        body {
            font-family: Arial, sans-serif;
                        background-color: #f0f8ff;
                        color: #000080;
                        display: flex;
                        justify-content: center;
                        align-items: center;
                        height: 100vh;
                        margin: 0;
        }
        .container {
            background-color: #e6f7ff;
            border: 1px solid #b3d1ff;
            padding: 20px;
            border-radius: 5px;
            max-width: 400px;
            width: 100%;
            box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
        }
        h2 {
            color: #000080;
            text-align: center;
        }
        label {
            font-weight: bold;
        }
        .input-group {
            position: relative;
            margin: 10px 0;
        }
        .input-group input,
        .input-group select {
            width: 100%;
            padding: 10px 10px 10px 40px; /* Adjust padding to make space for the icon */
            border: 1px solid #b3d1ff;
            border-radius: 5px;
            box-sizing: border-box;
        }
        .input-group .fa {
            position: absolute;
            left: 10px;
            top: 50%;
            transform: translateY(-50%);
            color: #000080;
        }
        input[type="submit"] {
            background-color: #000080;
            color: white;
            border: none;
            padding: 10px 20px;
            border-radius: 5px;
            cursor: pointer;
            width: 100%;
        }
        input[type="submit"]:hover {
            background-color: #0000cc;
        }
        .error {
            color: red;
            font-weight: bold;
        }
        p {
            text-align: center;
        }
        a {
            color: #0000cc;
        }
        a:hover {
            text-decoration: none;
            color: #000080;
        }
        select {
            appearance: none;
            background-color: #ffffff;
            background-image: url('data:image/svg+xml;charset=US-ASCII,%3Csvg xmlns%3D%27http://www.w3.org/2000/svg%27 viewBox%3D%270 0 4 5%27%3E%3Cpath fill%3D%27%23000000%27 d%3D%27M2 0L0 2h4zm0 5L0 3h4z%27/%3E%3C/svg%3E');
            background-repeat: no-repeat;
            background-position: right 10px top 50%;
            background-size: 10px 5px;
        }
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
