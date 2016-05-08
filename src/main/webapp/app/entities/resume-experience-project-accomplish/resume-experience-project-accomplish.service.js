(function() {
    'use strict';
    angular
        .module('resumeApp')
        .factory('ResumeExperienceProjectAccomplish', ResumeExperienceProjectAccomplish);

    ResumeExperienceProjectAccomplish.$inject = ['$resource'];

    function ResumeExperienceProjectAccomplish ($resource) {
        var resourceUrl =  'api/resume-experience-project-accomplishes/:id';

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
