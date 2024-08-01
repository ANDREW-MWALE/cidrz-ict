<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page import="java.sql.*" %>
<%@ page import="com.andyprofinnovations.dao.DBConnection" %>
<%@ page import="com.andyprofinnovations.model.Users" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>User Registration</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>
    <div class="container">
        <h2>User Registration</h2>
        <%
            String errorMessage = (String) request.getAttribute("errorMessage");
            if (errorMessage != null) {
        %>
        <div class="error"><%= errorMessage %></div>
        <% } %>

        <%
            // Initialize user data variables
            Users user = new Users();
            String user_id = request.getParameter("user_id");

            if (user_id != null && !user_id.isEmpty()) {
                Connection con = null;
                Statement stmt = null;
                ResultSet rs = null;
                try {
                    con = DBConnection.getConn();
                    stmt = con.createStatement();
                    String query = "SELECT * FROM users WHERE user_id = " + user_id;
                    rs = stmt.executeQuery(query);
                    if (rs.next()) {
                        user.setUser_id(rs.getInt("user_id"));
                        user.setName(rs.getString("name"));
                        user.setPosition(rs.getString("position"));
                        user.setEmail(rs.getString("email"));
                        user.setRole_id(rs.getInt("role_id"));
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    if (rs != null) try { rs.close(); } catch (SQLException ignore) {}
                    if (stmt != null) try { stmt.close(); } catch (SQLException ignore) {}
                    if (con != null) try { con.close(); } catch (SQLException ignore) {}
                }
            }
        %>

        <form action="UserServlet?action=register" method="post" id="userForm">
            <div class="form-group">
                <input type="hidden" class="form-control" id="user_id" name="user_id" value="<%= user.getUser_id() %>">
            </div>
            <div class="form-group">
                <label for="name">Name:</label>
                <input type="text" class="form-control" id="name" name="name" value="<%= user.getName() %>" required>
            </div>
            <div class="form-group">
                <label for="position">Position:</label>
                <input type="text" class="form-control" id="position" name="position" value="<%= user.getPosition() %>" required>
            </div>
            <div class="form-group">
                <label for="email">Email:</label>
                <input type="email" class="form-control" id="email" name="email" value="<%= user.getEmail() %>" required>
            </div>
            <div class="form-group">
                <label for="role_id">Role ID:</label>
                <select id="role_id" name="role_id" class="form-control" required>
                    <option value="">Choose your role</option>
                    <%
                        Connection conRole = null;
                        Statement stmtRole = null;
                        ResultSet rsRole = null;
                        try {
                            conRole = DBConnection.getConn();
                            stmtRole = conRole.createStatement();
                            String queryRole = "SELECT * FROM role";
                            rsRole = stmtRole.executeQuery(queryRole);
                            while (rsRole.next()) {
                                int roleId = rsRole.getInt("role_id");
                                String roleName = rsRole.getString("name");
                    %>
                    <option value="<%= roleId %>" <%= user.getRole_id() == roleId ? "selected" : "" %>><%= roleName %></option>
                    <%
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        } finally {
                            if (rsRole != null) try { rsRole.close(); } catch (SQLException ignore) {}
                            if (stmtRole != null) try { stmtRole.close(); } catch (SQLException ignore) {}
                            if (conRole != null) try { conRole.close(); } catch (SQLException ignore) {}
                        }
                    %>
                </select>
            </div>
            <div class="form-group">
                <label for="password">Password:</label>
                <input type="password" class="form-control" id="password" name="password" required>
            </div>
            <div class="form-group">
                <label for="confirm_password">Confirm Password:</label>
                <input type="password" class="form-control" id="confirm_password" name="confirm_password" required>
            </div>
            <button type="submit" class="btn-primary">Register</button>
            <p>Already have an account? <a href="login.jsp">Login</a></p>
        </form>
    </div>
</body>
</html>
