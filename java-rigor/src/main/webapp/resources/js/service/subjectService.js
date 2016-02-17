'use strict';

App.factory('SubjectService', ['$http', '$q', function ($http, $q) {
    return {

        getSubjectPrefixList: function () {
            return $http.get('/subject/prefix')
                .then(
                    function (response) {
                        return response.data;
                    },
                    function (errResponse) {
                        return $q.reject(errResponse);
                    }
                );
        }
        ,
        saveSubject: function (subject) {
            return $http.post('/subject/save', subject)
                .then(
                    function (response) {
                        return response.data;
                    },
                    function (errResponse) {
                        console.error('Error while updating student!');
                        return $q.reject(errResponse);
                    }
                );

        },

        fetchAllSubjects: function () {
            return $http.get('/subject/all')
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

          deleteSubject: function (id) {
                              return $http.delete('/subject/delete/' + id)
                                  .then(
                                      function (response) {
                                          return response.data;
                                      },
                                      function (errResponse) {
                                          console.error('Error while deleting subject!');
                                          return $q.reject(errResponse);
                                      }
                                );
                    },

       }

}]);