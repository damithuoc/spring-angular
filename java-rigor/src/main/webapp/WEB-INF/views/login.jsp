<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@page session="true" %>
<spring:url value="/resources/css/bootstrap.min.css" var="bootstrap"/>
    <spring:url value="/auth/login_check?targetUrl=${targetUrl}" var="loginValidation"/>

<!doctype html>
<html>
<head>
    <title>Login</title>
    <link href="${bootstrap}" rel="stylesheet"/>
</head>

<body onload='document.loginForm.username.focus();'>
<div class="container">
 <div class="row">
        <div class="col-xs-12 col-sm-12 col-md-10 col-md-offset-1 col-lg-8 col-lg-offset-2">
    <h2 class="text-center">Login </h2></br>
    <form class="form-horizontal" action="${loginValidation}" method='POST'>
        <div class="form-group">
            <label class="control-label col-sm-2" for="username">Username:</label>
            <div class="col-sm-7">
                <input type="text" class="form-control" id="username" name="username" placeholder="Enter Username">
            </div>
        </div>
        <div class="form-group">
            <label class="control-label col-sm-2" for="password">Password:</label>
            <div class="col-sm-7">
                <input type="password" class="form-control" id="password" name="password" placeholder="Enter Password">
            </div>
        </div>
        <div class="form-group">
            <label class="control-label col-sm-2">&nbsp;&nbsp;&nbsp;&nbsp;</label>
            <div class="col-sm-7">
                <input type="submit" name="submit" value="Submit" class="btn btn-primary"/>

            </div>
        </div>
        <input type="hidden" name="${_csrf.parameterName}"
               value="${_csrf.token}" />
    </form>
</div>
</div>
</div>

</body>

</html>
