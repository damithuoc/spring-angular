<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<spring:url value="/resources/js/app.js" var="appJs"/>
<spring:url value="/resources/js/controller/subjectController.js" var="subjectJs"/>
<spring:url value="/resources/js/service/subjectService.js" var="subjectService"/>
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

<body ng-app="welcomeModule">

<div class="container">
    </br>
    </br>
<div class ="row">
 <div class="col-xs-12 col-sm-12 col-md-10 col-md-offset-1 col-lg-8  col-lg-offset-2">
<nav class="navbar navbar-inverse" style="background-color:#449D44; border: none; color:white;" >
  <div class="container-fluid ">
    <div class="navbar-header" style="color:#fff;">
      <a class="navbar-brand" href="${stdHome}">Student Home</a>
    </div>
    <ul class="nav navbar-nav">
      <li class="active"><a href="#">Subject</a></li>
      <li ><a href="${subjectStudent}">Student Subjects</a></li>
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
      <span class="glyphicon glyphicon-log-out"></span>${pageContext.request.userPrincipal.name}| Logout</a></li>
    </ul>
  </div>
</nav>
</div>
</div>
<div class="container"    ng-controller="SubjectController as subjectCtrl">
      <div class="row">
        <div class="col-xs-12 col-sm-12 col-md-10 col-md-offset-1 col-lg-8 col-lg-offset-2">
            <h2>Add Subject</h2></br>
            <p style="color: red">{{subjectCtrl.errorView.message }} </p>
            <form class="form-horizontal" ng-submit="subjectCtrl.submit()" name="subjectCreationForm">
             <input type="hidden" ng-model="subjectCtrl.subject.id" />
                <div class="form-group">
                    <div class="row">
                        <label class="control-label col-sm-12 col-md-2 col-lg-2" for="subjectName">Subject Name:</label>
                        <div class="col-sm-12 col-md-10 col-lg-10">
                            <input type="text" class="form-control" id="subjectName" placeholder="Enter subject Name"
                                   ng-model="subjectCtrl.subject.subjectName">
                            <p style="color: red"> {{subjectCtrl.subjectNameErrorMsg}}</p>
                        </div>

                    </div>
                </div>
                <div class="form-group">
                    <div class="row">
                        <label class="control-label col-sm-12 col-md-2 col-lg-2">Subject Code:</label>
                        <div class="col-sm-4 col-md-3 col-lg-3">
                            <select class="form-control"
                              ng-model="prefix"
                              ng-options="subCode.prefix as  subCode.prefix  for subCode in subjectCtrl.subjectCodeList">
                              </select>
                        </div>
                        <div class="col-sm-6 col-md-5 col-lg-7">
                             <input  type="text" class="form-control" id="number" placeholder="Enter number with 4 digit, Ex: 2002"
                              ng-model="subjectCtrl.subject.subjectCode.codeNumber" >
                            <p style="color: red"> {{subjectCtrl.subjectCodeNumberErrorMsg}}</p>
                        </div>


                    </div>
                </div>
                <div class="form-group">
                    <div class="row">
                        <label class="control-label col-sm-12 col-md-2 col-lg-2">&nbsp;&nbsp;&nbsp;&nbsp;</label>
                        <div class="col-sm-12 col-md-10 col-lg-10">
                            <input type="submit" value="{{!subjectCtrl.subject.id ? 'Add' : 'Update'}}"
                                   class="btn btn-primary btn-success">
                            <button type="button" ng-click="subjectCtrl.reset()" class="btn btn-warning btn-danger"
                                    ng-disabled="subjectCreationForm.$pristine">Reset
                            </button>
                        </div>
                    </div>
                </div>
            </form>
        </div>
    </div>

    <div class="row">
        <div class="col-xs-12 col-sm-12 col-md-10 col-md-offset-1 col-lg-8 col-lg-offset-2">
            <h2>Subject Details </h2>
            <table class="table  table-hover">
                <thead>
                <tr>
                    <th>Subject Name</th>
                    <th>Subject Code</th>
                    <th>Edit</th>
                    <th>Delete</th>
                </tr>
                </thead>
                <tbody>
                <tr ng-repeat="subject in subjectCtrl.subjectList">
                    <td><span ng-bind="subject.subjectName"></span></td>
                    <td><span ng-bind="subject.subjectCode.subjectCodeStr"></span></td>
                    <td>
                        <button type="button" ng-click="subjectCtrl.edit(subject.id)"
                                class="btn btn-success custom-width">Edit
                        </button>
                    </td>
                    <td>
                        <button type="button" ng-click="subjectCtrl.remove(subject.id)"
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
    <script src="${subjectJs}"></script>
    <script src="${subjectService}"></script>

</body>

</html>
