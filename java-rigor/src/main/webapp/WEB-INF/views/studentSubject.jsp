<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<spring:url value="/resources/js/app.js" var="appJs"/>
<spring:url value="/resources/js/controller/studentSubjectController.js" var="studentSubjectJs"/>
<spring:url value="/resources/js/service/studentSubjectService.js" var="studentSubjectService"/>
<spring:url value="/resources/lib/angular.js" var="angularJs"/>
<spring:url value="/resources/lib/angular-route.js" var="angularJsRouter"/>
<spring:url value="/resources/css/bootstrap.min.css" var="bootstrap"/>
<c:url value="/student/home" var="stdHome"/>
<c:url value="/subject/" var="subject"/>
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
      <li class="active"><a href="#">Student Subjects</a></li>
      <li><a href="${subject}">Subject</a></li>
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
<div class="container"    ng-controller="StudentSubjectController as stdSubjCtrl">
      <div class="row">
        <div class="col-xs-12 col-sm-12 col-md-10 col-md-offset-1 col-lg-8 col-lg-offset-2">
            <h2>Add Subjects </h2></br>

            <form class="form-horizontal" ng-submit="stdSubjCtrl.submit()" name="addSubjectsForm">
             <input type="hidden" ng-model="stdSubjCtrl.student.id" />
                <div class="form-group">
                    <div class="row">
                        <label class="control-label col-sm-12 col-md-2 col-lg-2" for="studentName">Student Name:</label>
                        <div class="col-sm-12 col-md-10 col-lg-10">
                            <label>{{stdSubjCtrl.student.studentName}}</label>
                        </div>
                    </div>
                </div>
                <div class="form-group">
                    <div class="row" ng-repeat="subject in stdSubjCtrl.subjectList">

                        <label class="control-label col-sm-12 col-md-2 col-lg-2" ></label>

                     <div class="col-sm-6 col-md-5 col-lg-7">
                      <label>{{subject.subjectName}} {{subject.subjectCode.prefix}}-{{subject.subjectCode.codeNumber}}</label>
                       </div>
                       <div class="col-sm-4 col-md-3 col-lg-3">
                              <input type="checkbox"
                              name="selectedSubjectIds[]"
                              value="{{subject.id}}"
                              ng-model="subject.selected"
                              class="form-control" />
                       </div>
                    </div>
                </div>
                <div class="form-group">
                    <div class="row">
                        <label class="control-label col-sm-12 col-md-2 col-lg-2">&nbsp;&nbsp;&nbsp;&nbsp;</label>
                        <div class="col-sm-12 col-md-10 col-lg-10">
                            <input type="submit" value="{{!stdSubjCtrl.student.subjectList.length ? 'Add ' : 'Update '}}"
                                   class="btn btn-primary btn-success">
                            <button type="button" ng-click="stdSubjCtrl.reset()" class="btn btn-warning btn-danger"
                                    ng-disabled="addSubjectsForm.$pristine">Reset
                            </button>
                        </div>
                    </div>
                </div>
            </form>
        </div>
    </div>

    <div class="row">
        <div class="col-xs-12 col-sm-12 col-md-10 col-md-offset-1 col-lg-8 col-lg-offset-2">
            <h2>Student Subject Details </h2>
            <table class="table  table-hover">
                <thead>
                <tr>
                    <th>Student Name</th>
                    <th>Edit subjects </th>
                </tr>
                </thead>
                <tbody>
                <tr ng-repeat="student in stdSubjCtrl.studentList">
                    <td><span ng-bind="student.studentName"></span></td>
                    <td>
                        <button type="button" ng-click="stdSubjCtrl.addStudent(student.id)"
                                class="btn btn-primary custom-width">Edit Subject
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
    <script src="${studentSubjectJs}"></script>
    <script src="${studentSubjectService}"></script>

</body>

</html>
