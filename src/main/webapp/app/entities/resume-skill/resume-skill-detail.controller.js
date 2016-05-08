(function() {
    'use strict';

    angular
        .module('resumeApp')
        .controller('ResumeSkillDetailController', ResumeSkillDetailController);

    ResumeSkillDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'entity', 'ResumeSkill', 'Resume'];

    function ResumeSkillDetailController($scope, $rootScope, $stateParams, entity, ResumeSkill, Resume) {
        var vm = this;
        vm.resumeSkill = entity;
        
        var unsubscribe = $rootScope.$on('resumeApp:resumeSkillUpdate', function(event, result) {
            vm.resumeSkill = result;
        });
        $scope.$on('$destroy', unsubscribe);

    }
})();
