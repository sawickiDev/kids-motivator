<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
    <head>
        <title>Kids Motivator Login</title>

        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">

        <link rel="stylesheet"
              href="https://stackpath.bootstrapcdn.com/bootstrap/4.1.0/css/bootstrap.min.css" integrity="sha384-9gVQ4dYFwwWSjIDZnLEWnxCjeSWFphJiwGPXr1jddIhOegiu1FwO5qRGvFXOdJZ4" crossorigin="anonymous">
        <link rel="stylesheet"
              href="css/global.css"/>

    </head>
    <body class="page-container">
        <div class="container-fluid" style="padding: 0;">
            <nav class="navbar navbar-dark bg-dark">
                <span class="navbar-brand mb-0 h1" style="color:white;">Kids Motivator</span>
            </nav>

            <div class="row justify-content-center col-12" style="margin-top: 160px">
                <div class="card text-white bg-dark mb-3" style="max-width: 18rem;">
                    <div class="card-header">Log In</div>
                    <div class="card-body">
                        <form:form
                                action="${pageContext.request.contextPath}/km-auth"
                                method="POST">

                            <c:if test="${param.error != null}">
                                <div class="kd-error-box">
                                    Incorrect Credentials
                                </div>
                            </c:if>

                            <div class="input-group input-group-sm mb-3">
                                <div class="input-group-prepend">
                                    <span class="input-group-text" id="username-id-label">Name</span>
                                </div>
                                <input class="form-control" id="username-id" name="username" />
                            </div>

                            <div class="input-group input-group-sm mb-3">
                                <div class="input-group-prepend">
                                    <span class="input-group-text" id="password-id-label">Password</span>
                                </div>
                                <input class="form-control" id="password-id" type="password" name="password" />
                            </div>

                            <input class="btn btn-light btn-sm btn-block" type="submit" value="Log In" />
                        </form:form>
                    </div>
                </div>

            </div>
        </div>
        <script src="https://code.jquery.com/jquery-3.3.1.slim.min.js" integrity="sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo" crossorigin="anonymous"></script>
        <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.0/umd/popper.min.js" integrity="sha384-cs/chFZiN24E4KMATLdqdvsezGxaGsi4hLGOzlXwp5UZB1LY//20VyM2taTB4QvJ" crossorigin="anonymous"></script>
        <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.1.0/js/bootstrap.min.js" integrity="sha384-uefMccjFJAIv6A+rW+L4AHf99KvxDjWSu1z9VI8SKNVmz4sk7buKt/6v9KI65qnm" crossorigin="anonymous"></script>
    </body>
</html>
