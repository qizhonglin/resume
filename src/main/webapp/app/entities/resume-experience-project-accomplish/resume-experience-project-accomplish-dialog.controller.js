(function() {
    'use strict';

    angular
        .module('resumeApp')
        .controller('ResumeExperienceProjectAccomplishDialogController', ResumeExperienceProjectAccomplishDialogController);

    ResumeExperienceProjectAccomplishDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'ResumeExperienceProjectAccomplish', 'ResumeExperienceProject'];

    function ResumeExperienceProjectAccomplishDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, ResumeExperienceProjectAccomplish, ResumeExperienceProject) {
        var vm = this;
        vm.resumeExperienceProjectAccomplish = entity;
        vm.resumeexperienceprojects = ResumeExperienceProject.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        var onSaveSuccess = function (result) {
            $scope.$emit('resumeApp:resumeExperienceProjectAccomplishUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        };

        var onSaveError = function () {
            vm.isSaving = false;
        };

        vm.save = function () {
            vm.isSaving = true;
            if (vm.resumeExperienceProjectAccomplish.id !== null) {
                ResumeExperienceProjectAccomplish.update(vm.resumeExperienceProjectAccomplish, onSaveSuccess, onSaveError);
            } else {
                ResumeExperienceProjectAccomplish.save(vm.resumeExperienceProjectAccomplish, onSaveSuccess, onSaveError);
            }
        };

        vm.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
    }
})();
