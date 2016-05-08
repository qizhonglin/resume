(function() {
    'use strict';
    angular
        .module('resumeApp')
        .factory('Resume', Resume);

    Resume.$inject = ['$resource'];

    function Resume ($resource) {
        var resourceUrl =  'api/resumes/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    data = angular.fromJson(data);
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    }
})();
