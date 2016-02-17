'use strict';

App.factory('StudentService', ['$http', '$q', function ($http, $q) {

    return {

        fetchAllStudents: function () {
            return $http.get('/student/all')
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

        saveStudent: function (student) {
            return $http.put('/student/save', student)
                .then(
                    function (response) {
                        return response.data;
                    },
                    function (errResponse) {
                        console.error('Error while saving student!');
                        return $q.reject(errResponse);
                    }
                );
        },

        updateStudent: function (student, id) {
            return $http.post('/student/update/' + id, student)
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

        deleteStudent: function (id) {
            return $http.delete('/student/delete/' + id)
                .then(
                    function (response) {
                        return response.data;
                    },
                    function (errResponse) {
                        console.error('Error while deleting student!');
                        return $q.reject(errResponse);
                    }
                );
        }

    };

}]);

