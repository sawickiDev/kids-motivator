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
                        <li class="nav-item">
                            <a class="nav-link" href="${pageContext.request.contextPath}/dashboard"> Dashboard <span class="sr-only">(current)</span></a>
                        </li>
                        <li class="nav-item active">
                            <a class="nav-link" href="${pageContext.request.contextPath}/missions"> Missions </a>
                        </li>
                        <li class="nav-item">
                            <a class="nav-link" href="#"> Prizes </a>
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
                    <c:when test="${allMissions != null && allMissions.size() > 0}">
                            <div class="row justify-content-center">
                                    <c:forEach items="${allMissions}" var="miss" varStatus="status">
                                        <div class="col-4">
                                            <div class="card text-white bg-dark mb-3">
                                                <div class="d-flex p-1">
                                                    <div class="mr-auto p-2 card-header" style="text-transform: uppercase">
                                                        ${miss.title}
                                                    </div>
                                                    <a href="${pageContext.request.contextPath}/update-mission/${miss.id}" class="p-2" style="color:#189bb0;">
                                                        <i class="material-icons">
                                                            cached
                                                        </i>
                                                    </a>
                                                    <a href="${pageContext.request.contextPath}/delete-mission/${miss.id}" class="p-2" style="color:#b00003;">
                                                        <i class="material-icons">
                                                            delete_forever
                                                        </i>
                                                    </a>
                                                </div>
                                                <div class="card-body">
                                                    <h5 style="color: lawngreen">${miss.value} points</h5>
                                                    <h6 class="card-title"><i>Deadline : </i>${miss.dateFormat}</h6>
                                                    <h6 class="card-text"><i>Description: </i>${miss.description}</h6>
                                                    <h6 class="card-text" style="color: yellow"><i>Stage: </i>${miss.stage}</h6>
                                                    <h6 class="card-text"><i>Assignee: </i>${miss.assignedKid.userName}</h6>
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
                                            <p>You have no missions to assign. Add one using form below</p>
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
                                Save Mission
                            </div>
                            <div class="card-body">
                                <form:form
                                        modelAttribute="mission"
                                        action="${pageContext.request.contextPath}/add-mission"
                                        method="POST">
                                    <c:if test="${saveMissionError == true}">
                                        <div class="alert alert-danger" role="alert">
                                            COULDN'T CREATE MISSION
                                        </div>
                                    </c:if>
                                    <form:input type="hidden"
                                                path="id"/>
                                    <div class="form-group">
                                        <label for="title-id">Mission Title</label>
                                        <form:input type="text"
                                                    class="form-control"
                                                    id="title-id"
                                                    placeholder="Mission Title"
                                                    path="title" />
                                        <form:errors path="title" cssStyle="color:#FF4C4C" />
                                    </div>

                                    <div class="form-group">
                                        <label for="description-id">Mission Description</label>
                                        <form:textarea  class="form-control"
                                                        id="description-id"
                                                        placeholder="Mission Description"
                                                        path="description" />
                                        <form:errors path="description" cssStyle="color:#FF4C4C" />
                                    </div>

                                    <div class="form-group">
                                        <label for="value-id">Mission Value</label>
                                        <form:input type="number"
                                                    class="form-control"
                                                    id="value-id"
                                                    placeholder="Mission Description"
                                                    path="value" />
                                        <form:errors path="value" cssStyle="color:#FF4C4C" />
                                    </div>

                                    <div class="form-group">
                                        <label for="deadline-id">Deadline (YYYY-MM-DD)</label>
                                        <form:input type="text"
                                                    class="form-control"
                                                    id="deadline-id"
                                                    placeholder="Deadline (YYYY-MM-DD)"
                                                    path="dateFormat" />
                                        <form:errors path="dateFormat" cssStyle="color:#FF4C4C" />
                                    </div>

                                    <div class="form-group mb-3">
                                        <label for="stage-id">Stage</label>
                                        <form:select  cssClass="custom-select"
                                                      path="stage"
                                                      items="${stages}"
                                                      id="stage-id">
                                        </form:select>
                                    </div>

                                    <div class="form-group mb-3">
                                        <label for="stage-id">Asignee</label>
                                        <form:select  cssClass="custom-select"
                                                      path="assignedId"
                                                      id="stage-id">
                                            <form:option value="-1" label="---Select---" />
                                            <form:options
                                                    items="${kidsAvailable}"
                                                    itemLabel="userName"
                                                    itemValue="id" />
                                        </form:select>
                                    </div>

                                    <input class="btn btn-outline-success" type="submit" value="SUBMIT" />
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
