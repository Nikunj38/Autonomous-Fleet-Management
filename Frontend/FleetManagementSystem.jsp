<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
    String action = request.getParameter("action");
    String username = request.getParameter("username");
    String password = request.getParameter("password");
    String error = null;

    // Predefined list of valid usernames and passwords
    String[][] validUsers = {
        {"admin1", "password1"}, {"admin2", "password2"}, {"admin3", "password3"},
        {"admin4", "password4"}, {"admin5", "password5"}, {"admin6", "password6"},
        {"admin7", "password7"}, {"admin8", "password8"}, {"admin9", "password9"},
        {"admin10", "password10"}, {"user1", "userpass1"}, {"user2", "userpass2"},
        {"user3", "userpass3"}, {"user4", "userpass4"}, {"user5", "userpass5"},
        {"user6", "userpass6"}, {"user7", "userpass7"}, {"user8", "userpass8"},
        {"user9", "userpass9"}, {"user10", "userpass10"}
    };

    // Handle login process
    if ("login".equals(action)) {
        boolean isValid = false;

        for (String[] user : validUsers) {
            if (user[0].equals(username) && user[1].equals(password)) {
                isValid = true;
                break;
            }
        }

        if (isValid) {
            // Successful login
            session.setAttribute("username", username);
            response.sendRedirect("fms.jsp?action=dashboard");
        } else {
            // Login failed
            error = "Invalid username or password!";
        }
    }

    // Handle logout
    if ("logout".equals(action)) {
        session.invalidate();
        response.sendRedirect("fms.jsp?action=login");
    }
%>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Fleet Management System</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <style>
        body {
            background: linear-gradient(to right, #1d3557, #457b9d);
            color: #fff;
            font-family: 'Arial', sans-serif;
            min-height: 100vh;
            margin: 0;
            display: flex;
            justify-content: center;
            align-items: center;
        }
        .card {
            border-radius: 15px;
            overflow: hidden;
            box-shadow: 0px 4px 10px rgba(0, 0, 0, 0.3);
        }
        .spinner-border {
            display: none;
        }
    </style>
</head>
<body>
<%
    if ("dashboard".equals(action)) {
%>
    <!-- Dashboard Section -->
    <nav class="navbar bg-primary navbar-dark">
        <div class="container-fluid">
            <a class="navbar-brand">Fleet Dashboard</a>
            <form method="post" action="?action=logout">
                <button type="submit" class="btn btn-danger btn-sm">Logout</button>
            </form>
        </div>
    </nav>
    <div class="container mt-4">
        <h1 class="text-center">Welcome, <%= session.getAttribute("username") %>!</h1>
        <div class="row mt-4">
            <div class="col-md-4">
                <div class="card shadow p-3">
                    <h3>Manage Vehicles</h3>
                    <p>Track, add, and maintain your fleet vehicles.</p>
                </div>
            </div>
            <div class="col-md-4">
                <div class="card shadow p-3">
                    <h3>Route Optimization</h3>
                    <p>Plan and optimize fleet routes efficiently.</p>
                </div>
            </div>
            <div class="col-md-4">
                <div class="card shadow p-3">
                    <h3>Real-Time Monitoring</h3>
                    <p>Track vehicle location and performance in real-time.</p>
                </div>
            </div>
        </div>
    </div>
<%
    } else {
%>
    <!-- Login Section -->
    <div class="card p-4" style="max-width: 400px; margin: auto;">
        <h2 class="text-center mb-4">Fleet Management Login</h2>
        <form id="loginForm" method="post" action="?action=login">
            <div class="mb-3">
                <label for="username" class="form-label">Username</label>
                <input type="text" class="form-control" id="username" name="username" required>
            </div>
            <div class="mb-3">
                <label for="password" class="form-label">Password</label>
                <input type="password" class="form-control" id="password" name="password" required>
            </div>
            <button type="submit" class="btn btn-primary w-100">Login</button>
        </form>
        <% if (error != null) { %>
            <div class="alert alert-danger mt-3" role="alert">
                <%= error %>
            </div>
        <% } %>
        <div class="spinner-border text-light spinner" id="loadingSpinner" role="status">
            <span class="visually-hidden">Loading...</span>
        </div>
    </div>
    <script>
        document.getElementById("loginForm").addEventListener("submit", function (event) {
            const username = document.getElementById("username").value.trim();
            const password = document.getElementById("password").value.trim();
            const spinner = document.getElementById("loadingSpinner");

            if (!username || !password) {
                event.preventDefault();
                alert("Both fields are required!");
                return;
            }

            // Show spinner
            spinner.style.display = "block";
        });
    </script>
<%
    }
%>
</body>
</html>
