(function() {
    'use strict';

    angular
        .module('resumeApp')
        .controller('ResumeDeleteController',ResumeDeleteController);

    ResumeDeleteController.$inject = ['$uibModalInstance', 'entity', 'Resume'];

    function ResumeDeleteController($uibModalInstance, entity, Resume) {
        var vm = this;
        vm.resume = entity;
        vm.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
        vm.confirmDelete = function (id) {
            Resume.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        };
    }
})();
