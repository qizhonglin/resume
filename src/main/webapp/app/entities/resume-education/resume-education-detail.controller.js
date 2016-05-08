(function() {
    'use strict';

    angular
        .module('resumeApp')
        .controller('ResumeEducationDetailController', ResumeEducationDetailController);

    ResumeEducationDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'entity', 'ResumeEducation', 'Resume'];

    function ResumeEducationDetailController($scope, $rootScope, $stateParams, entity, ResumeEducation, Resume) {
        var vm = this;
        vm.resumeEducation = entity;
        
        var unsubscribe = $rootScope.$on('resumeApp:resumeEducationUpdate', function(event, result) {
            vm.resumeEducation = result;
        });
        $scope.$on('$destroy', unsubscribe);

    }
})();
