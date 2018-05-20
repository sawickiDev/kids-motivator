<%@ page import="java.util.ResourceBundle" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
    <head>
        <title>Kids Motivator Login</title>

        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">

        <script src="/webjars/jquery/3.0.0/jquery.js"></script>
        <link rel="stylesheet" type="text/css"
              href="/webjars/bootstrap/4.0.0/css/bootstrap.css" />
    </head>
    <body>
        <form:form action="${pageContext.request.contextPath}/km-auth" method="POST">

            <label for="username-id">Username</label>
            <input id="username-id" name="username" />

            <label for="password-id">Password</label>
            <input id="password-id" type="password" name="password" />

            <input type="submit" value="Submit" />
        </form:form>
    </body>
</html>
