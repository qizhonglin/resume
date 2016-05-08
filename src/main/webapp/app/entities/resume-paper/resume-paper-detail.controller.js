(function() {
    'use strict';

    angular
        .module('resumeApp')
        .controller('ResumePaperDetailController', ResumePaperDetailController);

    ResumePaperDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'entity', 'ResumePaper', 'Resume'];

    function ResumePaperDetailController($scope, $rootScope, $stateParams, entity, ResumePaper, Resume) {
        var vm = this;
        vm.resumePaper = entity;
        
        var unsubscribe = $rootScope.$on('resumeApp:resumePaperUpdate', function(event, result) {
            vm.resumePaper = result;
        });
        $scope.$on('$destroy', unsubscribe);

    }
})();
