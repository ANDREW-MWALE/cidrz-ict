<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page import="java.sql.Connection, java.sql.DriverManager, java.sql.Statement, java.sql.ResultSet, java.sql.SQLException" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>User Portal</title>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.4/css/all.min.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/combined_style.css">
    <style>
        /* Add your custom CSS styling here */
        .container {
            max-width: 500px;
            margin: 50px auto;
            text-align: center;
        }
        .form {
            display: none;
        }
        .form--active {
            display: block;
        }
        .input-group {
            margin-bottom: 15px;
        }
        .button {
            margin: 20px 0;
        }
        .error {
            color: red;
            margin-bottom: 10px;
        }
    </style>
</head>
<body>
    <div class="container">
        <img src="assets/CIDRZ-logo_HR_RGB.jpg" alt="Logo" width="400" height="80">
        <div class="message">
            <div class="btn-wrapper">
                <button class="button" id="signupBtn">Sign Up</button>
                <button class="button" id="loginBtn">Login</button>
            </div>
        </div>

        <!-- Error Message -->
        <%
            String errorMessage = (String)request.getAttribute("errorMessage");
            if (errorMessage != null) {
        %>
        <div class="error"><%= errorMessage %></div>
        <% } %>

        <!-- Registration Form -->
        <div class="form form--signup form--active">
            <h2>User Registration</h2>
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
                                String query = "SELECT * FROM role";
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

                <input type="submit" value="Register" class="button">
            </form>
        </div>

        <!-- Login Form -->
        <div class="form form--login">
            <h2>User Login</h2>
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

                <input type="submit" value="Login" class="button">
               
            </form>
        </div>
    </div>

    <script>
        const signupBtn = document.getElementById('signupBtn');
        const loginBtn = document.getElementById('loginBtn');
        const signupForm = document.querySelector('.form--signup');
        const loginForm = document.querySelector('.form--login');

        signupBtn.addEventListener('click', function() {
            signupForm.classList.add('form--active');
            loginForm.classList.remove('form--active');
        });

        loginBtn.addEventListener('click', function() {
            loginForm.classList.add('form--active');
            signupForm.classList.remove('form--active');
        });

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
