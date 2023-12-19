<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%--
  Created by IntelliJ IDEA.
  User: Asus
  Date: 7/3/2023
  Time: 7:45 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <link rel="stylesheet" href="assets/css/style.css" type="text/css">
    <title> Student Registration Login </title>
</head>
<body class="login-page-body">

<div class="login-page">
    <div class="form">
        <div class="login">
            <div class="login-header">
                <h1>Welcome!</h1>
                <p style="color: red">${msg}</p>
            </div>
        </div>
        <form:form class="login-form" action="/authenticate-user" method="post" modelAttribute="user"  >
            <div id="email_errormessage" class="text-danger" style="color : red"></div>
            <form:input type="text" id="email" placeholder="Email"    path="email"/>
            <div id="password_errormessage" class="text-danger"  style="color : red"></div>
            <form:input type="password" id="password" placeholder="Password"  path="password"/>
            <button type="submit" > login</button>

        </form:form>
        <p class="message">Not registered? <a href="${pageContext.request.contextPath}/userReg">Create an account</a></p>
    </div>
</div>
</body>
<script src="../assets/js/login.js"></script>
</html>