'use strict';

App.factory('UserService', ['$http', '$q', function ($http, $q) {

    return {

        fetchUserRoles: function () {
            return $http.get('/user/roles')
                .then(
                    function (response) {
                        console.log(response.data);
                        return response.data;
                    },
                    function (errResponse) {
                        console.error('Error while fetching user roles!');
                        return $q.reject(errResponse);
                    }
                );
        },

        fetchAllUsers: function () {
            return $http.get('/user/all')
                .then(
                    function (response) {
                        return response.data;
                    },
                    function (errResponse) {
                        console.error('Error while fetching users');
                        return $q.reject(errResponse);
                    }
                );
        },

        createUser: function (user) {
            return $http.put('/user/save', user)
                .then(
                    function (response) {
                        return response.data;
                    },
                    function (errResponse) {
                        console.error('Error while creating user');
                        return $q.reject(errResponse);
                    }
                );
        },

        editUserRoles: function (roleList, username) {
            return $http.post('/user/roles/edit/' + username, roleList)
                .then(
                    function (response) {
                        return response.data;
                    },
                    function (errResponse) {
                        console.error('Error while updating userS!');
                        return $q.reject(errResponse);
                    }
                );
        },


        deleteUser: function (id) {
            return $http.delete('/user/delete/' + id)
                .then(
                    function (response) {
                        return response.data;
                    },
                    function (errResponse) {
                        console.error('Error while deleting user');
                        return $q.reject(errResponse);
                    }
                );
        }

    };

}]);
