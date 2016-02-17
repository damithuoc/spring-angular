'use strict';

App.factory('StudentSubjectService', ['$http', '$q', function ($http, $q) {
    return {

        fetchAllSubjects: function () {
            return $http.get('/student/subject/subjects')
                .then(
                    function (response) {
                        return response.data;
                    },
                    function (errResponse) {
                        console.error('Error while fetching students!');
                        return $q.reject(errResponse);
                    }
                );
        },

         fetchAllStudents: function () {
                    return $http.get('/student/subject/students')
                        .then(
                            function (response) {
                                return response.data;
                            },
                            function (errResponse) {
                                console.error('Error while fetching students!');
                                return $q.reject(errResponse);
                            }
                        );
                },

         updateSubject: function ( subject, id) {
                    return $http.post('/subject/update/' + id, subject)
                        .then(
                            function (response) {
                                return response.data;
                            },
                            function (errResponse) {
                                console.error('Error while updating subject!');
                                return $q.reject(errResponse);
                            }
                        );
                },

         addSubjectsToStudents: function (subjectIdList , studentId) {
                             return $http.post('/student/subject/add/' + studentId, subjectIdList)
                                 .then(
                                     function (response) {
                                         return response.data;
                                     },
                                     function (errResponse) {
                                         console.error('Error while updating subject!');
                                         return $q.reject(errResponse);
                                     }
                               );
                     },

         editStudentSubjects: function(studentId,subjectList){
            return $http.post('/student/subject/edit/'+studentId, subjectList)
                .then(
                    function(response){
                        return response.data;
                    },
                    function(errResponse){
                        console.error('Error while editing student\'s subjects!');
                        return errResponse;
                    }

                );

         }

       }

}]);