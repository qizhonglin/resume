(function() {
    'use strict';

    angular
        .module('resumeApp')
        .controller('ResumeExperienceProjectAccomplishDetailController', ResumeExperienceProjectAccomplishDetailController);

    ResumeExperienceProjectAccomplishDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'entity', 'ResumeExperienceProjectAccomplish', 'ResumeExperienceProject'];

    function ResumeExperienceProjectAccomplishDetailController($scope, $rootScope, $stateParams, entity, ResumeExperienceProjectAccomplish, ResumeExperienceProject) {
        var vm = this;
        vm.resumeExperienceProjectAccomplish = entity;
        
        var unsubscribe = $rootScope.$on('resumeApp:resumeExperienceProjectAccomplishUpdate', function(event, result) {
            vm.resumeExperienceProjectAccomplish = result;
        });
        $scope.$on('$destroy', unsubscribe);

    }
})();
