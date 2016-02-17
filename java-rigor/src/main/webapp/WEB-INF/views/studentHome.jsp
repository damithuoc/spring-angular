<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<spring:url value="/resources/js/app.js" var="appJs"/>
<spring:url value="/resources/js/controller/studentController.js" var="studentController"/>
<spring:url value="/resources/js/service/studentService.js" var="studentService"/>
<spring:url value="/resources/lib/angular.js" var="angularJs"/>
<spring:url value="/resources/lib/angular-ui-router.min.js" var="angularJsUiRouter"/>
<spring:url value="/resources/lib/angular-route.js" var="angularJsRouter"/>
<spring:url value="/resources/css/bootstrap.min.css" var="bootstrap"/>
<c:url value="/student/home" var="stdHome"/>
<c:url value="/subject/" var="subject"/>
<c:url value="/student/subject" var="subjectStudent"/>
<c:url value="/user/home" var="usrHome"/>

<!doctype html>
<html>
<head>
    <title>Welcome!</title>
    <link href="${bootstrap}" rel="stylesheet"/>
</head>

<body ng-app="welcomeModule" class="ng-cloak">

<div class="container">
    </br>
    </br>
    <div class="row">
        <div class="col-xs-12 col-sm-12 col-md-10 col-md-offset-1 col-lg-8 col-lg-offset-2">
            <nav class="navbar navbar-inverse" style="background-color:#449D44; border: none; color:white;">
                <div class="container-fluid ">
                    <div class="navbar-header style=" color:#fff;
                    "">
                    <a class="navbar-brand" href="#">Student Home</a>
                </div>
                <ul class="nav navbar-nav">
                    <li class="active"><a href="#">Student</a></li>
                    <li><a href="${subject}">Subject</a></li>
                    <li><a href="${subjectStudent}">Student Subjects</a></li>
                    <li><a href="${usrHome}">User</a></li>
                </ul>
                <ul class="nav navbar-nav navbar-right">
                    <c:url value="/j_spring_security_logout" var="logoutUrl"/>
                    <form action="${logoutUrl}" method="post" id="logoutForm">
                        <input type="hidden" name="${_csrf.parameterName}"
                               value="${_csrf.token}"/>
                    </form>
                    <script>
                        function formSubmit() {
                            document.getElementById("logoutForm").submit();
                        }
                    </script>
                    <li><a href="javascript:formSubmit()">
                        <span class="glyphicon glyphicon-log-out"></span>${pageContext.request.userPrincipal.name}|
                        Logout</a></li>
                </ul>
        </div>
        </nav>
    </div>
</div>

<div class="container" ng-controller="StudentController as stdControl">
    <div class="row">
        <div class="col-xs-12 col-sm-12 col-md-10 col-md-offset-1 col-lg-8 col-lg-offset-2">
            <h2>Student Registration</h2></br>
            <p style="color: red">{{stdControl.errorView.message }} </p>
            <form class="form-horizontal" ng-submit="stdControl.submit()" name="registrationForm">
                <input type="hidden" ng-model="stdControl.student.id"/>
                <div class="form-group">
                    <div class="row">
                        <label class="control-label col-sm-12 col-md-2 col-lg-2" for="studentName">Student Name:</label>
                        <div class="col-sm-12 col-md-10 col-lg-10">
                            <input type="text" class="form-control" id="studentName" placeholder="Enter Student Name"
                                   ng-model="stdControl.student.studentName">
                            <p style="color: red">{{stdControl.studentNameErrorMessage}} </p>
                        </div>
                    </div>
                </div>
                <div class="form-group">
                    <div class="row">
                        <label class="control-label col-sm-12 col-md-2 col-lg-2" for="email">Email:</label>
                        <div class="col-sm-12 col-md-10 col-lg-10">
                            <input type="email" class="form-control" id="email" placeholder="Enter email"
                                   ng-model="stdControl.student.email">
                        </div>
                    </div>
                </div>
                <div class="form-group">
                    <div class="row">
                        <label class="control-label col-sm-12 col-md-2 col-lg-2" for="address">Address:</label>
                        <div class="col-sm-12 col-md-10 col-lg-10">
                            <input type="text" class="form-control" id="address" placeholder="Enter address"
                                   ng-model="stdControl.student.address">
                        </div>
                    </div>
                </div>
                <div class="form-group">
                    <div class="row">
                        <label class="control-label col-sm-12 col-md-2 col-lg-2">&nbsp;&nbsp;&nbsp;&nbsp;</label>
                        <div class="col-sm-12 col-md-10 col-lg-10">
                            <input type="submit" value="{{!stdControl.student.id ? 'Add' : 'Update'}}"
                                   class="btn btn-primary btn-success">
                            <button type="button" ng-click="stdControl.reset()" class="btn btn-warning btn-danger"
                                    ng-disabled="registrationForm.$pristine">Reset
                            </button>
                        </div>
                    </div>
                </div>
            </form>
        </div>
    </div>

    <div class="row">
        <div class="col-xs-12 col-sm-12 col-md-10 col-md-offset-1 col-lg-8 col-lg-offset-2">
            <h2>Students Details </h2>
            <table class="table  table-hover">
                <thead>
                <tr>
                    <th>Reg Number</th>
                    <th>Name</th>
                    <th>Email</th>
                    <th>Address</th>
                    <th>Edit</th>
                    <th>Delete</th>
                </tr>
                </thead>
                <tbody>
                <tr ng-repeat="student in stdControl.students">
                    <td><span ng-bind="student.registrationNumber"></span></td>
                    <td><span ng-bind="student.studentName"></span></td>
                    <td><span ng-bind="student.email"></span></td>
                    <td><span ng-bind="student.address"></span></td>
                    <td>
                        <button type="button" ng-click="stdControl.edit(student.id)"
                                class="btn btn-success custom-width">Edit
                        </button>
                    </td>
                    <td>
                        <button type="button" ng-click="stdControl.remove(student.id)"
                                class="btn btn-danger custom-width">Remove
                        </button>
                    </td>
                </tr>
                </tbody>
            </table>
        </div>

    </div>
</div>

<script src="${angularJs}"></script>
<script src="${angularJsRouter}"></script>
<script src="${appJs}"></script>
<script src="${studentController}"></script>
<script src="${studentService}"></script>

</body>

</html>
