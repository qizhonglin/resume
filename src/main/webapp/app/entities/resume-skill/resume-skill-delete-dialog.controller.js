(function() {
    'use strict';

    angular
        .module('resumeApp')
        .controller('ResumeSkillDeleteController',ResumeSkillDeleteController);

    ResumeSkillDeleteController.$inject = ['$uibModalInstance', 'entity', 'ResumeSkill'];

    function ResumeSkillDeleteController($uibModalInstance, entity, ResumeSkill) {
        var vm = this;
        vm.resumeSkill = entity;
        vm.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
        vm.confirmDelete = function (id) {
            ResumeSkill.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        };
    }
})();
