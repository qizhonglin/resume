(function() {
    'use strict';

    angular
        .module('resumeApp')
        .controller('ResumeExperienceProjectDeleteController',ResumeExperienceProjectDeleteController);

    ResumeExperienceProjectDeleteController.$inject = ['$uibModalInstance', 'entity', 'ResumeExperienceProject'];

    function ResumeExperienceProjectDeleteController($uibModalInstance, entity, ResumeExperienceProject) {
        var vm = this;
        vm.resumeExperienceProject = entity;
        vm.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
        vm.confirmDelete = function (id) {
            ResumeExperienceProject.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        };
    }
})();
