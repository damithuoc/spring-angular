<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
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
<c:url value="/user/home" var="usrHome"/>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title><tiles:insertAttribute name="title" ignore="true" /></title>
<link href="${bootstrap}" rel="stylesheet"/>
  <script src="${angularJs}"></script>
    <script src="${angularJsRouter}"></script>
    <script src="${appJs}"></script>
    <script src="${studentController}"></script>
    <script src="${studentService}"></script>

</head>
<body ng-app="welcomeModule" >
        <div><tiles:insertAttribute name="header" /></div>
        <div style="float:left;padding:10px;width:15%;"><tiles:insertAttribute name="menu" /></div>
        <div style="float:left;padding:10px;width:80%;border-left:1px solid pink;">
        <tiles:insertAttribute name="body" /></div>
        <div style="clear:both"><tiles:insertAttribute name="footer" /></div>
</body>
</html>