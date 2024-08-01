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
    <style>
        body {
            font-family: Arial, sans-serif;
            margin: 0;
            padding: 0;
            background-color: #f4f4f4;
        }
        .navbar, .sidebar {
            background-color: #333;
            overflow: hidden;
        }
        .navbar {
            position: fixed;
            top: 0;
            width: 100%;
            z-index: 1000;
        }
        .navbar a {
            float: left;
            display: block;
            color: #f2f2f2;
            text-align: center;
            padding: 14px 20px;
            text-decoration: none;
            transition: background-color 0.3s, color 0.3s;
        }
        .navbar a:hover {
            background-color: #ddd;
            color: black;
        }
        .navbar a i {
            margin-right: 8px;
        }
        .sidebar {
            height: 100%;
            width: 250px;
            position: fixed;
            top: 50px; /* Adjust based on navbar height */
            left: 0;
            overflow-x: hidden;
            padding-top: 20px;
        }
        .sidebar a {
            padding: 15px 25px;
            text-decoration: none;
            font-size: 18px;
            color: #f2f2f2;
            display: block;
            transition: background-color 0.3s, color 0.3s;
        }
        .sidebar a:hover {
            background-color: #ddd;
            color: black;
        }
        .sidebar a i {
            margin-right: 8px;
        }
        .sidebar .category-title {
            color: #bbb;
            padding: 15px 25px;
            text-transform: uppercase;
            font-size: 14px;
            letter-spacing: 1px;
            background-color: #444;
        }
        .container {
            margin-left: 270px; /* Adjusted for sidebar width */
            margin-top: 70px; /* Adjusted for navbar height */
            padding: 20px;
        }
        .grid-container {
            display: grid;
            grid-template-columns: repeat(auto-fit, minmax(200px, 1fr));
            gap: 20px;
        }
        .clickable-box {
            display: flex;
            align-items: center;
            justify-content: center;
            padding: 30px;
            font-size: 24px;
            text-align: center;
            color: #fff;
            background-color: #1E9FF2;
            border-radius: 15px;
            text-decoration: none;
            box-shadow: 0 9px #999;
            cursor: pointer;
            transition: background-color 0.3s, transform 0.3s;
        }
        .clickable-box:hover {
            background-color: #0d8dc3;
        }
        .clickable-box:active {
            background-color: #0d8dc3;
            box-shadow: 0 5px #666;
            transform: translateY(4px);
        }
        .clickable-box i {
            margin-right: 10px;
        }
    </style>
</head>
<body>
    <div class="navbar">
        <a href="home.jsp"><i class="fas fa-home"></i> Home</a>
        <a href="#info"><i class="fas fa-info-circle"></i> Info</a>
        <a href="helpDesk.jsp"><i class="fas fa-life-ring"></i> Help Desk</a>
    </div>

    <div class="sidebar">
        <div class="category-title">Forms</div>
        <a href="incident-form.jsp"><i class="fas fa-exclamation-circle"></i> Report Incident</a>
        <a href="requestForChange-form.jsp"><i class="fas fa-sync-alt"></i> Request for Change</a>
        <a href="role-form.jsp"><i class="fas fa-user-tag"></i> Role</a>
        <a href="location-form.jsp"><i class="fas fa-map-marker-alt"></i> Location</a>
        <a href="approval-form.jsp"><i class="fas fa-thumbs-up"></i> Approvals</a>
        <a href="edit-registration-form.jsp"><i class="fas fa-user-edit"></i> Edit Profile</a>
        <a href="incident-form.jsp"><i class="fas fa-exclamation-circle"></i> Report Incident</a>

        <div class="category-title">Lists</div>
        <a href="incident-list.jsp"><i class="fas fa-list"></i> Incident List</a>
        <a href="request-list.jsp"><i class="fas fa-list-alt"></i> Request List</a>
        <a href="helpDesk.jsp"><i class="fas fa-life-ring"></i> Help Desk</a>
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
                    <a href="user_incident_list.jsp" class="clickable-box"><i class="fas fa-list"></i> Incident List</a>
                    <a href="requestForChange-form.jsp" class="clickable-box"><i class="fas fa-sync-alt"></i> Request for Change</a>
                    <a href="helpDesk.jsp" class="clickable-box"><i class="fas fa-life-ring"></i> Help Desk</a>
                    <a href="contact.jsp" class="clickable-box"><i class="fas fa-envelope"></i> Contact Us</a>
                <% } %>
            <% } %>
        </div>
    </div>

</body>
</html>
