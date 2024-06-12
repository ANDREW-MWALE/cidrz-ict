<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>User Login</title>
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
    input[type="email"],
    input[type="password"] {
        width: 100%;
        padding: 10px;
        margin: 10px 0;
        border: 1px solid #b3d1ff;
        border-radius: 5px;
        box-sizing: border-box;
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
</style>
</head>
<body>
    <div class="container">
        <h2>User Login</h2>
        <%
            String errorMessage = (String)request.getAttribute("errorMessage");
            if (errorMessage != null) {
        %>
        <div class="error"><%= errorMessage %></div>
        <% } %>
        <form action="UserServlet" method="post">
            <input type="hidden" name="action" value="login">
            <label for="email">Email:</label><br>
            <input type="email" id="email" name="email" required><br>
            <label for="password">Password:</label><br>
            <input type="password" id="password" name="password" required><br>
            <input type="submit" value="Login"><br>
            <p>Don't have an account? <a href="registration.jsp">Register here</a></p>
        </form>
    </div>
</body>
</html>
