(function() {
    'use strict';

    angular
        .module('resumeApp')
        .controller('ResumeExperienceProjectController', ResumeExperienceProjectController);

    ResumeExperienceProjectController.$inject = ['$scope', '$state', 'ResumeExperienceProject', 'ResumeExperienceProjectSearch'];

    function ResumeExperienceProjectController ($scope, $state, ResumeExperienceProject, ResumeExperienceProjectSearch) {
        var vm = this;
        vm.resumeExperienceProjects = [];
        vm.loadAll = function() {
            ResumeExperienceProject.query(function(result) {
                vm.resumeExperienceProjects = result;
            });
        };

        vm.search = function () {
            if (!vm.searchQuery) {
                return vm.loadAll();
            }
            ResumeExperienceProjectSearch.query({query: vm.searchQuery}, function(result) {
                vm.resumeExperienceProjects = result;
            });
        };
        vm.loadAll();
        
    }
})();
