(function() {
    'use strict';

    angular
        .module('resumeApp')
        .controller('ResumePaperController', ResumePaperController);

    ResumePaperController.$inject = ['$scope', '$state', 'ResumePaper', 'ResumePaperSearch'];

    function ResumePaperController ($scope, $state, ResumePaper, ResumePaperSearch) {
        var vm = this;
        vm.resumePapers = [];
        vm.loadAll = function() {
            ResumePaper.query(function(result) {
                vm.resumePapers = result;
            });
        };

        vm.search = function () {
            if (!vm.searchQuery) {
                return vm.loadAll();
            }
            ResumePaperSearch.query({query: vm.searchQuery}, function(result) {
                vm.resumePapers = result;
            });
        };
        vm.loadAll();
        
    }
})();
