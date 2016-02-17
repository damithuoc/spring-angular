'use strict';

App.controller('StudentController', ['$scope', 'StudentService', function ($scope, StudentService) {
    var self = this;
    self.student = {id: null, registrationNumber: '', studentName: '', email: '', address: ''};
    self.students = [];
    self.errorView = {};
    self.studentNameErrorMessage = '';

    self.fetchAllStudents = function () {
        StudentService.fetchAllStudents()
            .then(
                function (d) {
                    self.students = d;
                },
                function (errResponse) {
                    console.error('Error while fetching students');
                }
            );
    };

    self.saveStudent = function (student) {
        if (self.student.studentName == null || self.student.studentName == '') {
            self.studentNameErrorMessage = 'Name is empty, Please enter valid name!'

        } else {
            StudentService.saveStudent(student)
                .then(
                    self.fetchAllStudents,
                    function (errResponse) {
                        self.errorView = errResponse.data;
                        console.error('Error while saving student.');
                    }
                );
            self.reset();
        }

    };

    self.updateStudent = function (student, id) {
        if (self.student.studentName == null || self.student.studentName == '') {
            self.studentNameErrorMessage = 'Name is empty, Please enter valid name!'

        } else {
            StudentService.updateStudent(student, id)
                .then(
                    self.fetchAllStudents,
                    function (errResponse) {
                        self.errorView = errResponse.data;
                        console.error('Error while updating student.');
                    }
                );
            self.reset();
        }

    };

    self.deleteStudent = function (id) {
        StudentService.deleteStudent(id)
            .then(
                self.fetchAllStudents,
                function (errResponse) {
                    console.error('Error while deleting student.');
                }
            );
    };
    self.fetchAllStudents();

    self.submit = function () {

        self.studentNameErrorMessage = '';

        if (self.student.id == null || self.student.id == "") {
            console.log('Saving New student', self.student);
            self.saveStudent(self.student);

        } else {
            self.updateStudent(self.student, self.student.id);

            console.log('Student updated with id ', self.student.id);
        }
    };

    self.edit = function (id) {
        self.studentNameErrorMessage = '';
        console.log('id to be edited', id);
        for (var i = 0; i < self.students.length; i++) {
            if (self.students[i].id == id) {
                self.student = angular.copy(self.students[i]);
                break;
            }
        }
    };

    self.remove = function (id) {
        console.log('id to be deleted', id);
        for (var i = 0; i < self.students.length; i++) {
            if (self.students[i].id == id) {
                self.reset();
                break;
            }
        }
        self.deleteStudent(id);
    };

    self.reset = function () {
        self.student = {id: null, registrationNumber: '', studentName: '', email: '', address: ''};
        $scope.registrationForm.$setPristine(); //reset  registration Form
    };

}]);
