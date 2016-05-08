(function() {
    'use strict';

    angular
        .module('resumeApp')
        .controller('ResumePaperDialogController', ResumePaperDialogController);

    ResumePaperDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'ResumePaper', 'Resume'];

    function ResumePaperDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, ResumePaper, Resume) {
        var vm = this;
        vm.resumePaper = entity;
        vm.resumes = Resume.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        var onSaveSuccess = function (result) {
            $scope.$emit('resumeApp:resumePaperUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        };

        var onSaveError = function () {
            vm.isSaving = false;
        };

        vm.save = function () {
            vm.isSaving = true;
            if (vm.resumePaper.id !== null) {
                ResumePaper.update(vm.resumePaper, onSaveSuccess, onSaveError);
            } else {
                ResumePaper.save(vm.resumePaper, onSaveSuccess, onSaveError);
            }
        };

        vm.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
    }
})();
