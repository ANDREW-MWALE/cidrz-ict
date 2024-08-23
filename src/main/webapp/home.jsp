<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page import="com.andyprofinnovations.model.RoleConstants" %>
<%@ page import="com.andyprofinnovations.model.Users, com.andyprofinnovations.model.Role" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Dashboard</title>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0-beta3/css/all.min.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/home.css">
</head>
<body>
 <div class="navbar">
         <a href="home.jsp">Home</a>
         <a href="#info">Info</a>
         <a href="helpDesk.jsp">Help Desk</a>
     </div>

     <div class="sidebar">
         <div class="category-title">Forms</div>
         <a href="incident-form.jsp">Report Incident</a>
         <a href="requestForChange-form.jsp">Request for Change</a>

         <!-- Only display these links for admin users -->
         <c:if test="${loggedInUser.role_id == RoleConstants.ADMIN}">
             <a href="role-form.jsp">Role</a>
             <a href="location-form.jsp">Location</a>
             <a href="approval-form.jsp">Approvals</a>
         </c:if>


         <a href="edit-registration-form.jsp">Edit Profile</a>

         <div class="category-title">Lists</div>
         <a href="incident-list.jsp">Incident List</a>
         <a href="request-list.jsp">Request List</a>
         <a href="helpDesk.jsp">Help Desk</a>
     </div>

    <div class="container">
        <h1>ACTIONS</h1>
        <div class="grid-container">
            <%
                Users loggedInUser = (Users) session.getAttribute("loggedInUser");
                if (loggedInUser == null) {
                    out.println("<p>No user in session</p>");
                } else {
                    out.println("<p>User: " + loggedInUser.getName() + " with Role ID: " + loggedInUser.getRole_id() + "</p>");
                }
            %>
            <% if (loggedInUser != null) { %>
                <h4>Welcome, <%= loggedInUser.getName() %></h4>
                <p>Your role is: <%= loggedInUser.getRole_id() %></p><br>
                <% if(loggedInUser.getRole_id() == RoleConstants.ADMIN) { %>
                    <a href="incident-list.jsp" class="clickable-box"><i class="fas fa-list"></i> Incident List</a>
                    <a href="request-list.jsp" class="clickable-box"><i class="fas fa-list-alt"></i> Request List</a>
                <% } %>
                <% if(loggedInUser.getRole_id() == RoleConstants.ADMIN || loggedInUser.getRole_id() == RoleConstants.USER) { %>
                    <a href="incident-form.jsp" class="clickable-box"><i class="fas fa-exclamation-circle"></i> Incident Form</a>
                    <a href="incident-list.jsp" class="clickable-box"><i class="fas fa-list"></i> Incident List</a>
                    <a href="requestForChange-form.jsp" class="clickable-box"><i class="fas fa-sync-alt"></i> Request for Change</a>
                    <a href="maintenance-form.jsp" class="clickable-box">Maintenance</a>
                    <a href="helpDesk.jsp" class="clickable-box"><i class="fas fa-life-ring"></i> Help Desk</a>
                    <a href="contact.jsp" class="clickable-box"><i class="fas fa-envelope"></i> Contact Us</a>
                <% } %>
            <% } %>
        </div>
    </div>

</body>
</html>
