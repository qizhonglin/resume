(function() {
    'use strict';

    angular
        .module('resumeApp')
        .controller('ResumeEducationController', ResumeEducationController);

    ResumeEducationController.$inject = ['$scope', '$state', 'ResumeEducation', 'ResumeEducationSearch'];

    function ResumeEducationController ($scope, $state, ResumeEducation, ResumeEducationSearch) {
        var vm = this;
        vm.resumeEducations = [];
        vm.loadAll = function() {
            ResumeEducation.query(function(result) {
                vm.resumeEducations = result;
            });
        };

        vm.search = function () {
            if (!vm.searchQuery) {
                return vm.loadAll();
            }
            ResumeEducationSearch.query({query: vm.searchQuery}, function(result) {
                vm.resumeEducations = result;
            });
        };
        vm.loadAll();
        
    }
})();
