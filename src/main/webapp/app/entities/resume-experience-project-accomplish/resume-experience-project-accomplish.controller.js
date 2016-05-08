(function() {
    'use strict';

    angular
        .module('resumeApp')
        .controller('ResumeExperienceProjectAccomplishController', ResumeExperienceProjectAccomplishController);

    ResumeExperienceProjectAccomplishController.$inject = ['$scope', '$state', 'ResumeExperienceProjectAccomplish', 'ResumeExperienceProjectAccomplishSearch'];

    function ResumeExperienceProjectAccomplishController ($scope, $state, ResumeExperienceProjectAccomplish, ResumeExperienceProjectAccomplishSearch) {
        var vm = this;
        vm.resumeExperienceProjectAccomplishes = [];
        vm.loadAll = function() {
            ResumeExperienceProjectAccomplish.query(function(result) {
                vm.resumeExperienceProjectAccomplishes = result;
            });
        };

        vm.search = function () {
            if (!vm.searchQuery) {
                return vm.loadAll();
            }
            ResumeExperienceProjectAccomplishSearch.query({query: vm.searchQuery}, function(result) {
                vm.resumeExperienceProjectAccomplishes = result;
            });
        };
        vm.loadAll();
        
    }
})();
