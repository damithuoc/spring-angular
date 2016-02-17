'use strict';

App.controller('UserController', ['$scope', 'UserService', function ($scope, UserService) {
    var self = this;
    self.user = {id: null, username: '', password: '', roleList: [{id: null, name: '', isActive: false}]};
    self.users = [];
    $scope.roles = [];
    self.selectedRoles = [];
    self.editRole = false;
    self.errorView = {};
    self.usernameErrorMessage = '';
    self.passwordErrorMessage = '';
    self.roleListErrorMessage = '';
    self.successMessage = '';

    self.fetchUserRoles = function () {
        UserService.fetchUserRoles()
            .then(
                function (d) {
                    $scope.roles = d;
                },
                function (errResponse) {
                    console.error('Error while fetching user roles!');
                }
            );

    };

    self.fetchAllUsers = function () {
        UserService.fetchAllUsers()
            .then(
                function (d) {
                    self.users = d;
                },
                function (errResponse) {
                    self.errorView = errResponse.data;
                    console.error('Error while fetching users!');
                }
            );
    };

    self.createUser = function (user) {
        angular.forEach($scope.roles, function (role) {
            if (!!role.selected) self.selectedRoles.push(role);
        });

        self.user.roleList = self.selectedRoles;

        if (self.user.username == null || self.user.username == '') {
            self.usernameErrorMessage = 'Username is empty, Please enter valid username!';

        }
        else if (self.user.password == null || self.user.password == '') {
            self.passwordErrorMessage = 'Password is empty, Please enter valid password!';
            self.usernameErrorMessage = '';

        }
        else if (self.user.roleList == null || self.user.roleList.length == 0) {
            self.roleListErrorMessage = 'Role list is empty, Please select at least 1 role!';
            self.usernameErrorMessage = '';
            self.passwordErrorMessage = '';

        } else {
            UserService.createUser(user)
                .then(
                    self.fetchAllUsers,
                    function (errResponse) {
                        self.errorView = errResponse.data;
                        console.error('Error while creating User.');
                    }
                );
            self.usernameErrorMessage = '';
            self.passwordErrorMessage = '';
            self.roleListErrorMessage = '';

            self.reset();
        }

    };

    self.editUserRoles = function (roleList, username) {
        self.selectedRolesEdit = [];
        angular.forEach($scope.roles, function (role) {
            if (role.selected) {
                role.isActive = true;

            } else {
                role.isActive = false;
            }
            self.selectedRolesEdit.push(role);
        });

        if (self.selectedRolesEdit == null || self.selectedRolesEdit.length == 0) {
            self.roleListErrorMessage = 'Role list empty, Please select at least 1 role!';

        } else {
            UserService.editUserRoles(self.selectedRolesEdit, username)
                .then(
                    self.fetchAllUsers,
                    function (errResponse) {
                        self.errorView = errResponse.data;
                        console.error('Error while updating user.');
                    }
                );

            self.roleListErrorMessage = '';
            self.usernameErrorMessage = '';

            self.editRole = false;
            self.reset();
        }

    };

    self.deleteUser = function (id) {
        UserService.deleteUser(id)
            .then(
                self.fetchAllUsers,
                function (errResponse) {
                    self.errorView = errResponse.data;
                    console.error('Error while deleting User.');
                }
            );

    };

    self.fetchAllUsers();
    self.fetchUserRoles();

    self.submit = function () {
        if (self.user.id == null) {
            console.log('Saving New User', self.user);
            self.createUser(self.user);

        } else {
            self.editUserRoles(self.selectedRolesEdit, self.user.username);
            console.log('User roles edit with username ', self.user.username);
        }
    };

    self.edit = function (username) {
        self.editRole = true;
        console.log('id to be edited', username);
        for (var i = 0; i < self.users.length; i++) {
            if (self.users[i].username == username) {
                self.user = angular.copy(self.users[i]);
                break;
            }
        }

        angular.forEach($scope.roles, function (dbRole) {
            dbRole.selected = false;
        });

        self.userRoleList = self.user.roleList;
        angular.forEach($scope.roles, function (dbRole) {
            angular.forEach(self.userRoleList, function (userRole) {
                if (dbRole.name == userRole.name) {
                    dbRole.selected = true;
                }
            });
        });
    };

    self.remove = function (id) {
        console.log('id to be deleted', id);
        for (var i = 0; i < self.users.length; i++) {
            if (self.users[i].id == id) {
                self.reset();
                break;
            }
        }
        self.deleteUser(id);
    };

    self.reset = function () {
        self.user = {id: null, username: '', password: '', roleList: [{id: null, name: ''}]};
        self.checkedState = false;
        angular.forEach($scope.roles, function (role) {
            role.selected = self.checkedState;
        });
        $scope.userCreationForm.$setPristine(); //reset userCreation Form
    };

}]);
