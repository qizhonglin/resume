(function() {
    'use strict';
    angular
        .module('resumeApp')
        .factory('ResumeSkill', ResumeSkill);

    ResumeSkill.$inject = ['$resource'];

    function ResumeSkill ($resource) {
        var resourceUrl =  'api/resume-skills/:id';

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
