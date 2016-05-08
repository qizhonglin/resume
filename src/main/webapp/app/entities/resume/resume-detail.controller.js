(function() {
    'use strict';

    angular
        .module('resumeApp')
        .controller('ResumeDetailController', ResumeDetailController);

    ResumeDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'entity', 'Resume', 'User', 'ResumeExperience', 'ResumeSkill', 'ResumePaper', 'ResumeEducation'];

    function ResumeDetailController($scope, $rootScope, $stateParams, entity, Resume, User, ResumeExperience, ResumeSkill, ResumePaper, ResumeEducation) {
        var vm = this;
        vm.resume = entity;
        
        var unsubscribe = $rootScope.$on('resumeApp:resumeUpdate', function(event, result) {
            vm.resume = result;
        });
        $scope.$on('$destroy', unsubscribe);

    }
})();
