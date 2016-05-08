(function() {
    'use strict';

    angular
        .module('resumeApp')
        .controller('ResumeDialogController', ResumeDialogController);

    ResumeDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', '$q', 'entity', 'Resume', 'User', 'ResumeExperience', 'ResumeSkill', 'ResumePaper', 'ResumeEducation'];

    function ResumeDialogController ($timeout, $scope, $stateParams, $uibModalInstance, $q, entity, Resume, User, ResumeExperience, ResumeSkill, ResumePaper, ResumeEducation) {
        var vm = this;
        vm.resume = entity;
        vm.users = User.query();
        vm.resumeexperiences = ResumeExperience.query();
        vm.resumeskills = ResumeSkill.query();
        vm.resumepapers = ResumePaper.query();
        vm.resumeeducations = ResumeEducation.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        var onSaveSuccess = function (result) {
            $scope.$emit('resumeApp:resumeUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        };

        var onSaveError = function () {
            vm.isSaving = false;
        };

        vm.save = function () {
            vm.isSaving = true;
            if (vm.resume.id !== null) {
                Resume.update(vm.resume, onSaveSuccess, onSaveError);
            } else {
                Resume.save(vm.resume, onSaveSuccess, onSaveError);
            }
        };

        vm.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
    }
})();
