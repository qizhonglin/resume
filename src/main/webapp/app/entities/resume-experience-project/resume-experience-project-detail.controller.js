(function() {
    'use strict';

    angular
        .module('resumeApp')
        .controller('ResumeExperienceProjectDetailController', ResumeExperienceProjectDetailController);

    ResumeExperienceProjectDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'entity', 'ResumeExperienceProject', 'ResumeExperienceProjectAccomplish', 'ResumeExperience'];

    function ResumeExperienceProjectDetailController($scope, $rootScope, $stateParams, entity, ResumeExperienceProject, ResumeExperienceProjectAccomplish, ResumeExperience) {
        var vm = this;
        vm.resumeExperienceProject = entity;
        
        var unsubscribe = $rootScope.$on('resumeApp:resumeExperienceProjectUpdate', function(event, result) {
            vm.resumeExperienceProject = result;
        });
        $scope.$on('$destroy', unsubscribe);

    }
})();
