(function() {
    'use strict';

    angular
        .module('resumeApp')
        .controller('ResumeSkillController', ResumeSkillController);

    ResumeSkillController.$inject = ['$scope', '$state', 'ResumeSkill', 'ResumeSkillSearch'];

    function ResumeSkillController ($scope, $state, ResumeSkill, ResumeSkillSearch) {
        var vm = this;
        vm.resumeSkills = [];
        vm.loadAll = function() {
            ResumeSkill.query(function(result) {
                vm.resumeSkills = result;
            });
        };

        vm.search = function () {
            if (!vm.searchQuery) {
                return vm.loadAll();
            }
            ResumeSkillSearch.query({query: vm.searchQuery}, function(result) {
                vm.resumeSkills = result;
            });
        };
        vm.loadAll();
        
    }
})();
