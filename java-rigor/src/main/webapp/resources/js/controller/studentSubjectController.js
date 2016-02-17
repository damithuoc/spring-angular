'use strict';

App.controller('StudentSubjectController', ['$scope', 'StudentSubjectService', function ($scope, StudentSubjectService) {
    var self = this;
    self.subject = {id: null, subjectCode: {id:null,prefix:'',codeNumber:null }, subjectName: '', isActive: false};
    self.subjectList = [];
    self.studentList = [];
    self.student = {id:null, studentName:'', subjectList:''};
    self.selectedSubjectIds = [];
    $scope.subjectId = null;

    self.fetchAllSubjects = function () {
        StudentSubjectService.fetchAllSubjects()
            .then(
                function (d) {
                    self.subjectList = d;
                },
                function (errResponse) {
                    console.error('Error while fetching subjects');
                }
            );
    };

    self.fetchAllStudents = function () {
            StudentSubjectService.fetchAllStudents()
                .then(
                    function (d) {
                        self.studentList = d;
                    },
                    function (errResponse) {
                        console.error('Error while fetching students');
                    }
                );
        };

      self.addSubjectsToStudents = function (subjectIdList, studentId) {
            StudentSubjectService.addSubjectsToStudents(subjectIdList, studentId)
                .then(
                    self.fetchAllStudents,
                    function (errResponse) {
                        console.error('Error while updating student.');
                    }
                );
        };

        self.editStudentSubjects = function(studentId, subjectList){
            StudentSubjectService.editStudentSubjects(studentId, subjectList)
            .then(
                self.fetchAllStudents,
                function(errResponse){
                    console.error('Error while editing student\'s subjects!')
                }
            );
        };

    self.fetchAllSubjects();
    self.fetchAllStudents();

    self.submit = function () {
        if (self.student.subjectList == null || self.student.subjectList.length == 0) {
                angular.forEach(self.subjectList, function(subject){
                     if (!!subject.selected ) {
                        self.selectedSubjectIds.push(subject.id);
                        }
                    });

                self.addSubjectsToStudents(self.selectedSubjectIds, self.student.id);
                console.log('Saving new subject for student', self.subject);

        } else {
            self.editSubjectList = [];
            angular.forEach(self.subjectList, function(subject){
                if (!!subject.selected ) {
                    subject.isActive = true;
                }else{
                    subject.isActive = false;
                }
                self.editSubjectList.push(subject);
            });
            self.editStudentSubjects(self.student.id,self.editSubjectList );
            console.log('Subject add with id ', self.student.id);
        }
        self.reset();
    };

    self.edit = function (id) {
        console.log('id to be edited', id);
        for (var i = 0; i < self.subjectList.length; i++) {
            if (self.subjectList[i].id == id) {
                self.subject = angular.copy(self.subjectList[i]);
                break;
            }
        }
    };

     self.addStudent = function (id) {
            console.log('id to be edited', id);

            angular.forEach(self.subjectList, function(subject){
                subject.selected = false;
            });

            for (var i = 0; i < self.studentList.length; i++) {
                if (self.studentList[i].id == id) {
                    self.student = angular.copy(self.studentList[i]);
                    break;
                }
            }

            self.studentSubjectList = [];

            angular.forEach(self.student.subjectList, function(studentSubject){
                                    self.studentSubjectList.push(studentSubject);
                                           }
                                        );

            if(self.studentSubjectList.length == 0){
                angular.forEach(self.subjectList, function(dbSubject){
                     dbSubject.selected = false;
                });
            }

            angular.forEach(self.subjectList, function(dbSubject){
                angular.forEach(self.studentSubjectList, function(studentSelectedSubject){
                   if(dbSubject.id == studentSelectedSubject.id){
                   dbSubject.selected = true;
                   }
                }
                );
            });

        };

    self.remove = function (id) {
        console.log('id to be deleted', id);
        for (var i = 0; i < self.subjectList.length; i++) {
            if (self.subjectList[i].id == id) {
                self.reset();
                break;
            }
        }
              self.deleteSubject(id);
    };

    self.reset = function () {
        self.subject = {id: null, subjectCode: {id:null,prefix:'',codeNumber:null }, subjectName: ''};
        self.student = {id:null, studentName:'', subjectList:''};
        angular.forEach(self.subjectList, function(dbSubject){
                            dbSubject.selected = false;
                           });
        $scope.addSubjectsForm.$setPristine(); //reset  addSubjectsForm Form
    };

}]);
