(function() {
    'use strict';

    angular
        .module('resumeApp')
        .controller('ResumePaperDeleteController',ResumePaperDeleteController);

    ResumePaperDeleteController.$inject = ['$uibModalInstance', 'entity', 'ResumePaper'];

    function ResumePaperDeleteController($uibModalInstance, entity, ResumePaper) {
        var vm = this;
        vm.resumePaper = entity;
        vm.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
        vm.confirmDelete = function (id) {
            ResumePaper.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        };
    }
})();
