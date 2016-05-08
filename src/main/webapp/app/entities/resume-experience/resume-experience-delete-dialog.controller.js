(function() {
    'use strict';

    angular
        .module('resumeApp')
        .controller('ResumeExperienceDeleteController',ResumeExperienceDeleteController);

    ResumeExperienceDeleteController.$inject = ['$uibModalInstance', 'entity', 'ResumeExperience'];

    function ResumeExperienceDeleteController($uibModalInstance, entity, ResumeExperience) {
        var vm = this;
        vm.resumeExperience = entity;
        vm.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
        vm.confirmDelete = function (id) {
            ResumeExperience.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        };
    }
})();
