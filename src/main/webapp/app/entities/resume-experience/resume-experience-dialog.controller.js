(function() {
    'use strict';

    angular
        .module('resumeApp')
        .controller('ResumeExperienceDialogController', ResumeExperienceDialogController);

    ResumeExperienceDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'ResumeExperience', 'ResumeExperienceProject', 'Resume'];

    function ResumeExperienceDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, ResumeExperience, ResumeExperienceProject, Resume) {
        var vm = this;
        vm.resumeExperience = entity;
        vm.resumeexperienceprojects = ResumeExperienceProject.query();
        vm.resumes = Resume.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        var onSaveSuccess = function (result) {
            $scope.$emit('resumeApp:resumeExperienceUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        };

        var onSaveError = function () {
            vm.isSaving = false;
        };

        vm.save = function () {
            vm.isSaving = true;
            if (vm.resumeExperience.id !== null) {
                ResumeExperience.update(vm.resumeExperience, onSaveSuccess, onSaveError);
            } else {
                ResumeExperience.save(vm.resumeExperience, onSaveSuccess, onSaveError);
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
