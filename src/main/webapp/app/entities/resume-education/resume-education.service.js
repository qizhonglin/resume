(function() {
    'use strict';
    angular
        .module('resumeApp')
        .factory('ResumeEducation', ResumeEducation);

    ResumeEducation.$inject = ['$resource', 'DateUtils'];

    function ResumeEducation ($resource, DateUtils) {
        var resourceUrl =  'api/resume-educations/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    data = angular.fromJson(data);
                    data.startTime = DateUtils.convertDateTimeFromServer(data.startTime);
                    data.endTime = DateUtils.convertDateTimeFromServer(data.endTime);
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    }
})();
