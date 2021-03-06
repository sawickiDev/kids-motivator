<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
    <head>
        <title>Kids Motivator Dashboard</title>

        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">

        <link rel="stylesheet"
              href="https://stackpath.bootstrapcdn.com/bootstrap/4.1.0/css/bootstrap.min.css" integrity="sha384-9gVQ4dYFwwWSjIDZnLEWnxCjeSWFphJiwGPXr1jddIhOegiu1FwO5qRGvFXOdJZ4" crossorigin="anonymous">
        <link rel="stylesheet"
              href="css/global.css"/>
        <link href="https://fonts.googleapis.com/icon?family=Material+Icons"
              rel="stylesheet">
    </head>
    <body class="page-container">
        <div class="container-fluid" style="padding: 0;">
            <nav class="navbar navbar-expand-lg navbar-dark bg-dark">
                <a class="navbar-brand" href="${pageContext.request.contextPath}/dashboard"> Kids Motivator </a>
                <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarNav" aria-controls="navbarNav" aria-expanded="false" aria-label="Toggle navigation">
                    <span class="navbar-toggler-icon"></span>
                </button>
                <div class="collapse navbar-collapse justify-content-between" id="navbarNav">
                    <ul class="navbar-nav">
                        <li class="nav-item active">
                            <a class="nav-link" href="${pageContext.request.contextPath}/dashboard"> Dashboard <span class="sr-only">(current)</span></a>
                        </li>
                        <li class="nav-item">
                            <a class="nav-link" href="${pageContext.request.contextPath}/missions"> Missions </a>
                        </li>
                        <li class="nav-item">
                            <a class="nav-link" href="${pageContext.request.contextPath}/prizes"> Prizes </a>
                        </li>
                    </ul>

                    <form:form  class="mt-1 mb-1"
                                style="margin-bottom=0px"
                                method="post"
                                action="/logout-page">
                        <button class="btn btn-outline-info" type="submit">Logout</button>
                    </form:form>
                </div>
            </nav>
            <div class="container mt-5">
                <c:choose>
                    <c:when test="${kidsList != null && kidsList.size() > 0}">
                    <div class="row justify-content-center">
                        <c:forEach items="${kidsList}" var="kid">
                            <div class="col-4">
                                <div class="card text-white bg-dark mb-3">
                                    <div>
                                        <div class="mr-auto p-2 card-header d-flex p-1 justify-content-between" style="text-transform: uppercase">
                                                ${kid.firstName} ${kid.lastName}
                                            <a href="${pageContext.request.contextPath}/toggle-kid-active/${kid.id}" style="color:#189bb0;">
                                                <c:if test="${kid.active == true}">
                                                    <i class="material-icons">
                                                        visibility
                                                    </i>
                                                </c:if>
                                                <c:if test="${kid.active == false}">
                                                    <i class="material-icons">
                                                        visibility_off
                                                    </i>
                                                </c:if>
                                            </a>
                                        </div>
                                    </div>
                                    <div class="card-body">
                                        <h5 style="color: lawngreen">${kid.sumPoints} points</h5>
                                        <h6 class="card-title"><i>Missions Assigned : </i>${kid.sumMissions}</h6>
                                    </div>
                                </div>
                            </div>
                        </c:forEach>
                    </div>
                    </c:when>
                    <c:otherwise>
                        <div class="row justify-content-center">
                            <div class="col-10">
                                <div class="card text-white bg-dark mb-3">
                                    <div class="card-header">
                                        Ooops...
                                    </div>
                                    <div class="card-body">
                                        <blockquote class="blockquote mb-0">
                                            <p>You have no kids to manage. Add one using form below</p>
                                        </blockquote>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </c:otherwise>
                </c:choose>
                <div class="row justify-content-center">
                    <div class="col-10">
                        <div class="card text-white bg-secondary mb-3">
                            <div class="card-header">
                                Register Kid
                            </div>
                            <div class="card-body">
                                <form:form
                                        modelAttribute="kid"
                                        action="${pageContext.request.contextPath}/register-kid"
                                        method="POST">
                                    <c:if test="${saveKidError == true}">
                                        <div class="alert alert-danger" role="alert">
                                            COULDN'T REGISTER KID
                                        </div>
                                    </c:if>
                                    <div class="form-row">
                                        <div class="col-4">
                                            <label for="firstname-id">First name</label>
                                            <form:input type="text"
                                                        class="form-control"
                                                        id="firstname-id"
                                                        placeholder="First name"
                                                        path="firstName" />
                                            <form:errors path="firstName" cssStyle="color:#FF4C4C" />
                                        </div>

                                        <div class="col-4">
                                            <label for="lastname-id">Last Name</label>
                                            <form:input type="text"
                                                        class="form-control"
                                                        id="lastname-id"
                                                        placeholder="Last name"
                                                        path="lastName" />
                                            <form:errors path="lastName" cssStyle="color:#FF4C4C" />
                                        </div>

                                        <div class="col-4">
                                            <label for="firstname-id">Username</label>
                                            <form:input type="text"
                                                        class="form-control"
                                                        id="username-id"
                                                        placeholder="Username"
                                                        path="userName" />
                                            <form:errors path="userName" cssStyle="color:#FF4C4C" />
                                        </div>
                                    </div>

                                    <div class="form-row">

                                        <div class="col-4">
                                            <label for="password-id">Password</label>
                                            <form:password  class="form-control"
                                                            id="password-id"
                                                            placeholder="Password"
                                                            path="pass" />
                                            <form:errors path="pass.password" cssStyle="color:#FF4C4C" />
                                        </div>

                                        <div class="col-4">
                                            <label for="cpassword-id">Confirm Password</label>
                                            <form:password  class="form-control"
                                                            id="cpassword-id"
                                                            placeholder="Password"
                                                            path="pass.confirmPassword" />
                                            <form:errors path="pass.confirmPassword" cssStyle="color:#FF4C4C" />
                                            <form:errors path="pass" cssStyle="color:#FF4C4C" />
                                        </div>
                                    </div>

                                    <div class="form-row mt-4">
                                        <div class="col-2">
                                            <input class="btn btn-outline-success" type="submit" value="Register" />
                                        </div>
                                    </div>
                                </form:form>
                            </div>
                        </div>
                    </div>
                </div>
            </div>


        </div>
        <script src="https://code.jquery.com/jquery-3.3.1.slim.min.js" integrity="sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo" crossorigin="anonymous"></script>
        <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.0/umd/popper.min.js" integrity="sha384-cs/chFZiN24E4KMATLdqdvsezGxaGsi4hLGOzlXwp5UZB1LY//20VyM2taTB4QvJ" crossorigin="anonymous"></script>
        <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.1.0/js/bootstrap.min.js" integrity="sha384-uefMccjFJAIv6A+rW+L4AHf99KvxDjWSu1z9VI8SKNVmz4sk7buKt/6v9KI65qnm" crossorigin="anonymous"></script>
    </body>
</html>
