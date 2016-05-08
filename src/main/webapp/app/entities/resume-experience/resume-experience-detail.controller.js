(function() {
    'use strict';

    angular
        .module('resumeApp')
        .controller('ResumeExperienceDetailController', ResumeExperienceDetailController);

    ResumeExperienceDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'entity', 'ResumeExperience', 'ResumeExperienceProject', 'Resume'];

    function ResumeExperienceDetailController($scope, $rootScope, $stateParams, entity, ResumeExperience, ResumeExperienceProject, Resume) {
        var vm = this;
        vm.resumeExperience = entity;
        
        var unsubscribe = $rootScope.$on('resumeApp:resumeExperienceUpdate', function(event, result) {
            vm.resumeExperience = result;
        });
        $scope.$on('$destroy', unsubscribe);

    }
})();
