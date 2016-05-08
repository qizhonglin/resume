(function() {
    'use strict';

    angular
        .module('resumeApp')
        .controller('ResumeEducationDeleteController',ResumeEducationDeleteController);

    ResumeEducationDeleteController.$inject = ['$uibModalInstance', 'entity', 'ResumeEducation'];

    function ResumeEducationDeleteController($uibModalInstance, entity, ResumeEducation) {
        var vm = this;
        vm.resumeEducation = entity;
        vm.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
        vm.confirmDelete = function (id) {
            ResumeEducation.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        };
    }
})();
