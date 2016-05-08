(function() {
    'use strict';

    angular
        .module('resumeApp')
        .controller('ResumeSkillDialogController', ResumeSkillDialogController);

    ResumeSkillDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'ResumeSkill', 'Resume'];

    function ResumeSkillDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, ResumeSkill, Resume) {
        var vm = this;
        vm.resumeSkill = entity;
        vm.resumes = Resume.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        var onSaveSuccess = function (result) {
            $scope.$emit('resumeApp:resumeSkillUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        };

        var onSaveError = function () {
            vm.isSaving = false;
        };

        vm.save = function () {
            vm.isSaving = true;
            if (vm.resumeSkill.id !== null) {
                ResumeSkill.update(vm.resumeSkill, onSaveSuccess, onSaveError);
            } else {
                ResumeSkill.save(vm.resumeSkill, onSaveSuccess, onSaveError);
            }
        };

        vm.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
    }
})();
