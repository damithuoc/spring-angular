<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<spring:url value="/resources/js/app.js" var="appJs"/>
<spring:url value="/resources/js/controller/userController.js" var="userController"/>
<spring:url value="/resources/js/service/userService.js" var="userService"/>
<spring:url value="/resources/lib/angular.js" var="angularJs"/>
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

<body ng-app="welcomeModule" ng-controller="UserController as usrControl">

<div class="container">
    </br>
    </br>
    <div class="row">
        <div class="col-xs-12 col-sm-12 col-md-10 col-md-offset-1 col-lg-8 col-lg-offset-2">
            <nav class="navbar navbar-inverse" style="background-color:#449D44; border: none; color:white;">
                <div class="container-fluid ">
                    <div class="navbar-header" style="color:#fff;">
                        <a class="navbar-brand" href="#">User Home</a>
                    </div>
                    <ul class="nav navbar-nav">
                        <li class="active"><a href="#">User</a></li>
                        <li><a href="${stdHome}">Student</a></li>
                        <li><a href="${subject}">Subject</a></li>
                        <li><a href="${subjectStudent}">Student Subjects</a></li>
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

    <div class="row">
        <div class="col-xs-12 col-sm-12 col-md-10 col-md-offset-1 col-lg-8 col-lg-offset-2">
            <div class="pull-right">

            </div>
        </div>
    </div>
    <div class="row check-element animate-hide" id="addUserFormDiv" ng-hide="usrControl.editRole">
        <div class="col-xs-12 col-sm-12 col-md-10 col-md-offset-1 col-lg-8 col-lg-offset-2">
            <h2>User Account</h2></br>
            <p style="color: red">{{usrControl.errorView.message }} </p>
            <form class="form-horizontal" role="form" ng-submit="usrControl.submit()" name="userCreationForm">
                <input type="hidden" ng-model="usrControl.user.id"/>
                <div class="form-group">
                    <div class="row">
                        <label class="control-label col-sm-12 col-md-2 col-lg-2" for="username">Username:</label>
                        <div class="col-sm-12 col-md-10 col-lg-10">
                            <input type="text" class="form-control" id="username" placeholder="Enter  Username"
                                   ng-model="usrControl.user.username" ng-disabled="data.check"/>
                            <p style="color: red"> {{usrControl.usernameErrorMessage}}</p>
                        </div>
                    </div>
                </div>
                <div class="form-group">
                    <div class="row">
                        <label class="control-label col-sm-12 col-md-2 col-lg-2" for="password">Password:</label>
                        <div class="col-sm-12 col-md-10 col-lg-10">
                            <input type="password" class="form-control" id="password" placeholder="Enter password"
                                   ng-model="usrControl.user.password" ng-disabled="data.check"/>
                            <p style="color: red"> {{usrControl.passwordErrorMessage}}</p>
                        </div>
                    </div>
                </div>
                <div class="form-group">
                    <div class="row">
                        <label class="control-label col-sm-12 col-md-2 col-lg-2" for="role">User Role:</label>

                        <div ng-repeat="role in roles" class="col-sm-3 col-md-2 col-lg-2">
                            <input type="checkbox"
                                   name="selectedRoles[]"
                                   value="{{role.name}}"
                                   ng-model="role.selected"
                                   class="form-control"/>
                            <label> {{ role.name }}</label>

                        </div>


                    </div>
                    <div class="row">
                        <p style="color: red"> {{usrControl.roleListErrorMessage}}</p>

                    </div>

                </div>
                <div class="form-group">
                    <div class="row">
                        <label class="control-label col-sm-12 col-md-2 col-lg-2">&nbsp;&nbsp;&nbsp;&nbsp;</label>
                        <div class="col-sm-12 col-md-10 col-lg-10">
                            <input type="submit" value="{{!usrControl.user.id ? 'Add' : 'Update'}}"
                                   class="btn btn-primary btn-success">
                            <button type="button" ng-click="usrControl.reset()" class="btn btn-warning btn-danger"
                                    ng-disabled="userCreationForm.$pristine">Reset
                            </button>
                        </div>
                    </div>
                </div>
            </form>
        </div>
    </div>


    <div id="userRoleEditDiv" class="row check-element animate-hide" ng-show="usrControl.editRole">
        <div class="col-xs-12 col-sm-12 col-md-10 col-md-offset-1 col-lg-8 col-lg-offset-2">


            <form class="form-horizontal" role="form" ng-submit="usrControl.submit()" name="roleEditForm">
                <input type="hidden" ng-model="usrControl.user.username"/>
                <div class="form-group">
                    <div class="row">
                        <label class="control-label col-sm-12 col-md-2 col-lg-2" for="role">User Role:</label>

                        <div ng-repeat="role in roles" class="col-sm-3 col-md-2 col-lg-2">
                            <input type="checkbox"
                                   name="selectedRoles[]"
                                   value="{{role.name}}"
                                   ng-model="role.selected"
                                   class="form-control"/>
                            <label> {{ role.name }}</label>

                        </div>
                    </div>
                    <div class="row">
                        <p style="color: red"> {{usrControl.roleListErrorMessage}}</p>

                    </div>
                </div>
                <div class="form-group">
                    <div class="row">
                        <label class="control-label col-sm-12 col-md-2 col-lg-2">&nbsp;&nbsp;&nbsp;&nbsp;</label>
                        <div class="col-sm-12 col-md-10 col-lg-10">
                            <input type="submit" value="{{!usrControl.user.username ? 'Add' : 'Update'}}"
                                   class="btn btn-primary btn-success">
                            <button type="button" ng-click="usrControl.reset()" class="btn btn-warning btn-danger"
                                    ng-disabled="roleEditForm.$pristine">Reset
                            </button>
                        </div>
                    </div>
                </div>
            </form>
        </div>
    </div>


    <div class="row">
        <div class="col-xs-12 col-sm-12 col-md-10 col-md-offset-1 col-lg-8 col-lg-offset-2">
            <h2>User Details </h2>
            <table class="table  table-hover">
                <thead>
                <tr>
                    <th>Username</th>
                    <th>User Roles</th>
                    <th>Edit User Role</th>
                    <th>Delete</th>
                </tr>
                </thead>
                <tbody>
                <tr ng-repeat="user in usrControl.users">
                    <td><span ng-bind="user.username"></span></td>
                    <td>
                    <span ng-repeat="role in user.roleList">
                      <span ng-bind="role.name"></span></br>
                    </span>
                    </td>
                    <td>
                        <button type="button" ng-click="usrControl.edit(user.username)"
                                class="btn btn-success custom-width">Change Role
                        </button>
                    </td>
                    <td>
                        <button type="button" ng-click="usrControl.remove(user.id)"
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
<script src="${userController}"></script>
<script src="${userService}"></script>
</body>

</html>
