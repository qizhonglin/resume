(function() {
    'use strict';

    angular
        .module('resumeApp')
        .controller('ResumeExperienceController', ResumeExperienceController);

    ResumeExperienceController.$inject = ['$scope', '$state', 'ResumeExperience', 'ResumeExperienceSearch'];

    function ResumeExperienceController ($scope, $state, ResumeExperience, ResumeExperienceSearch) {
        var vm = this;
        vm.resumeExperiences = [];
        vm.loadAll = function() {
            ResumeExperience.query(function(result) {
                vm.resumeExperiences = result;
            });
        };

        vm.search = function () {
            if (!vm.searchQuery) {
                return vm.loadAll();
            }
            ResumeExperienceSearch.query({query: vm.searchQuery}, function(result) {
                vm.resumeExperiences = result;
            });
        };
        vm.loadAll();
        
    }
})();
