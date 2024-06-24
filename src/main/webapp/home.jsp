<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Home Page</title>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0-beta3/css/all.min.css">
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
        .sidebar {
            height: 100%;
            width: 250px;
            position: fixed;
            top: 0;
            left: 0;
            background-color: #333;
            overflow-x: hidden;
            padding-top: 20px;
        }
        .sidebar a {
            padding: 15px 25px;
            text-decoration: none;
            font-size: 18px;
            color: #f2f2f2;
            display: block;
            transition: 0.3s;
        }
        .sidebar a:hover {
            background-color: #ddd;
            color: black;
        }
        .container {
            margin-left: 270px; /* Adjusted for sidebar width */
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
        <a href="#home">Home</a>
        <a href="#report">Report Incident</a>
        <a href="#info">Info</a>
        <a href="#help">Help Desk</a>
        <a href="role-form.jsp">Role</a>
        <a href="location-form.jsp">Location</a>
    </div>

    <div class="sidebar">
        <a href="#home">Home</a>
        <a href="#report">Report Incident</a>
        <a href="#info">Request for Change</a>
        <a href="#help">Help Desk</a>
        <a href="role-form.jsp">Role</a>
        <a href="location-form.jsp">Location</a>
    </div>

    <div class="container">
        <h1>ACTIONS</h1>
        <div class="grid-container">
            <a href="incident-list.jsp" class="clickable-box"><i class="fas fa-exclamation-circle"></i>Report Incident</a>
            <a href="requestForChange-form.jsp" class="clickable-box"><i class="fas fa-sync"></i> Request for Change</a>
            <a href="helpDesk.jsp" class="clickable-box"><i class="fas fa-life-ring"></i>Help Desk</a>
            <a href="contactUs.jsp" class="clickable-box"><i class="fas fa-envelope"></i>Contact Us</a>
        </div>
    </div>

</body>
</html>
