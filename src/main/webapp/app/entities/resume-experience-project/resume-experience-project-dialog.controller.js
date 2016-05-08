(function() {
    'use strict';

    angular
        .module('resumeApp')
        .controller('ResumeExperienceProjectDialogController', ResumeExperienceProjectDialogController);

    ResumeExperienceProjectDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'ResumeExperienceProject', 'ResumeExperienceProjectAccomplish', 'ResumeExperience'];

    function ResumeExperienceProjectDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, ResumeExperienceProject, ResumeExperienceProjectAccomplish, ResumeExperience) {
        var vm = this;
        vm.resumeExperienceProject = entity;
        vm.resumeexperienceprojectaccomplishes = ResumeExperienceProjectAccomplish.query();
        vm.resumeexperiences = ResumeExperience.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        var onSaveSuccess = function (result) {
            $scope.$emit('resumeApp:resumeExperienceProjectUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        };

        var onSaveError = function () {
            vm.isSaving = false;
        };

        vm.save = function () {
            vm.isSaving = true;
            if (vm.resumeExperienceProject.id !== null) {
                ResumeExperienceProject.update(vm.resumeExperienceProject, onSaveSuccess, onSaveError);
            } else {
                ResumeExperienceProject.save(vm.resumeExperienceProject, onSaveSuccess, onSaveError);
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
