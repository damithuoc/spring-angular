'use strict';

App.controller('SubjectController', ['$scope', 'SubjectService', function ($scope, SubjectService) {
    var self = this;
    self.subject = {id: null, subjectCode: {id: null, prefix: '', codeNumber: null}, subjectName: ''};
    self.subjectList = [];
    $scope.prefix = '';
    self.subjectCodeList = [];
    self.errorView = {};
    self.subjectNameErrorMsg = '';
    self.subjectCodeNumberErrorMsg = '';

    self.getSubjectPrefixList = function () {
        SubjectService.getSubjectPrefixList()
            .then(
                function (d) {
                    self.subjectCodeList = d;
                    $scope.prefix = self.subjectCodeList[0].prefix;
                },
                function (errResponse) {
                    console.error('Error while fetching subject prefixes!');
                }
            );
    };

    self.fetchAllSubjects = function () {
        SubjectService.fetchAllSubjects()
            .then(
                function (d) {
                    self.subjectList = d;
                },
                function (errResponse) {
                    console.error('Error while fetching subjects');
                }
            );
    };

    self.saveSubject = function (subject) {

        if (self.subject.subjectName == null || self.subject.subjectName == '') {
            self.subjectNameErrorMsg = 'Subject name is empty, please enter valid subject name!';

        } else if (self.subject.subjectCode.codeNumber == null || self.subject.subjectCode.codeNumber == '') {
            self.subjectCodeNumberErrorMsg = 'Subject code number is empty, Please enter valid subject code number!'
        }
        else {
            SubjectService.saveSubject(subject)
                .then(
                    self.fetchAllSubjects,
                    function (errResponse) {
                        self.errorView = errResponse.data;
                        console.error('Error while saving subject!');
                    }
                );

            self.reset();
        }

    };

    self.updateSubject = function (subject, id) {

        if (self.subject.subjectName == null || self.subject.subjectName == '') {
            self.subjectNameErrorMsg = 'Subject name is empty, please enter valid subject name!';

        } else if (self.subject.subjectCode.codeNumber == null || self.subject.subjectCode.codeNumber == '') {
            self.subjectCodeNumberErrorMsg = 'Subject code number is empty, Please enter valid subject code number!'
        }
        else {
            SubjectService.updateSubject(subject, id)
                .then(
                    self.fetchAllSubjects,
                    function (errResponse) {
                        self.errorView = errResponse.data;
                        console.error('Error while updating student.');
                    }
                );

        }
    };

    self.deleteSubject = function (id) {
        SubjectService.deleteSubject(id)
            .then(
                self.fetchAllSubjects,
                function (errResponse) {
                    console.error('Error while deleting student.');
                }
            );
    };

    self.fetchAllSubjects();
    self.getSubjectPrefixList();

    self.submit = function () {
        self.subject.subjectCode.prefix = $scope.prefix;
        if (self.subject.id == null || self.subject.id == "") {
            console.log('Saving new subject', self.subject);
            self.saveSubject(self.subject);

        } else {
            self.updateSubject(self.subject, self.subject.id);
            console.log('Subject updated with id ', self.subject.id);
        }

    };

    self.edit = function (id) {
        console.log('id to be edited', id);
        self.errorView = {};
        self.subjectNameErrorMsg = '';
        self.subjectCodeNumberErrorMsg = '';

        for (var i = 0; i < self.subjectList.length; i++) {
            if (self.subjectList[i].id == id) {
                self.subject = angular.copy(self.subjectList[i]);
                $scope.prefix = self.subjectList[i].subjectCode.prefix;
                break;
            }
        }
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
        self.subject = {id: null, subjectCode: '', subjectName: ''};
        $scope.prefix = self.subjectCodeList[0].prefix;
        $scope.subjectCreationForm.$setPristine(); //reset  subjectCreation Form
    };

}]);
