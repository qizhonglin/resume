(function() {
    'use strict';
    angular
        .module('resumeApp')
        .factory('ResumePaper', ResumePaper);

    ResumePaper.$inject = ['$resource'];

    function ResumePaper ($resource) {
        var resourceUrl =  'api/resume-papers/:id';

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
