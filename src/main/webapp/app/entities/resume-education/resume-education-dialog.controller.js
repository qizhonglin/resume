(function() {
    'use strict';

    angular
        .module('resumeApp')
        .controller('ResumeEducationDialogController', ResumeEducationDialogController);

    ResumeEducationDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'ResumeEducation', 'Resume'];

    function ResumeEducationDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, ResumeEducation, Resume) {
        var vm = this;
        vm.resumeEducation = entity;
        vm.resumes = Resume.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        var onSaveSuccess = function (result) {
            $scope.$emit('resumeApp:resumeEducationUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        };

        var onSaveError = function () {
            vm.isSaving = false;
        };

        vm.save = function () {
            vm.isSaving = true;
            if (vm.resumeEducation.id !== null) {
                ResumeEducation.update(vm.resumeEducation, onSaveSuccess, onSaveError);
            } else {
                ResumeEducation.save(vm.resumeEducation, onSaveSuccess, onSaveError);
            }
        };

        vm.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };

        vm.datePickerOpenStatus = {};
        vm.datePickerOpenStatus.startTime = false;
        vm.datePickerOpenStatus.endTime = false;

        vm.openCalendar = function(date) {
            vm.datePickerOpenStatus[date] = true;
        };
    }
})();
