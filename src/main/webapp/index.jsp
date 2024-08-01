<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>User Login</title>
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
            background-color: rgba(230, 247, 255, 0.9); /* Adjust background opacity as needed */
            border: 1px solid #b3d1ff;
            padding: 20px;
            border-radius: 9px;
            max-width: 400px;
            width: 100%;
            box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
            text-align: center;
        }
        h2 {
            color: #000080;
        }
        label {
            font-weight: bold;
        }
        .input-group {
            position: relative;
            margin: 10px 0;
        }
        .input-group input {
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
    </style>
</head>
<body>
    <div class="container">
        <img src="assets/CIDRZ-logo_HR_RGB.jpg" alt="Logo" width="400" height="80">
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
            <div class="input-group">
                <i class="fa fa-envelope"></i>
                <input type="email" id="email" name="email" required>
            </div>
            <label for="password">Password:</label><br>
            <div class="input-group">
                <i class="fa fa-eye" id="togglePassword"></i>
                <input type="password" id="password" name="password" required>
            </div>
            <input type="submit" value="Login"><br>
            <p>Don't have an account? <a href="registration.jsp">Register here</a></p>
        </form>
    </div>
    <script>
        const togglePassword = document.querySelector('#togglePassword');
        const password = document.querySelector('#password');

        togglePassword.addEventListener('click', function (e) {
            const type = password.getAttribute('type') === 'password' ? 'text' : 'password';
            password.setAttribute('type', type);
            this.classList.toggle('fa-eye-slash');
        });
    </script>
</body>
</html>
