'use strict';

var App = angular.module('welcomeModule', ['ngRoute']);
App.config(function ($routeProvider) {
    $routeProvider.when('/home', {
        templateUrl: 'studentHome',
        controller: 'StudentController'
    });

    $routeProvider.when('/user/home', {
        templateUrl: 'userHome',
        controller: 'UserController'
    });

    $routeProvider.when('/subject/', {
        templateUrl: 'subject',
        controller: 'SubjectController'
    });

    $routeProvider.when('/student/subject', {
        templateUrl: 'studentSubject',
        controller: 'StudentSubjectController'
    });

    $routeProvider.otherwise({
        redirectTo: '/'
    });
});