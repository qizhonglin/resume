(function() {
    'use strict';

    angular
        .module('resumeApp')
        .controller('ResumeExperienceProjectAccomplishDeleteController',ResumeExperienceProjectAccomplishDeleteController);

    ResumeExperienceProjectAccomplishDeleteController.$inject = ['$uibModalInstance', 'entity', 'ResumeExperienceProjectAccomplish'];

    function ResumeExperienceProjectAccomplishDeleteController($uibModalInstance, entity, ResumeExperienceProjectAccomplish) {
        var vm = this;
        vm.resumeExperienceProjectAccomplish = entity;
        vm.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
        vm.confirmDelete = function (id) {
            ResumeExperienceProjectAccomplish.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        };
    }
})();
