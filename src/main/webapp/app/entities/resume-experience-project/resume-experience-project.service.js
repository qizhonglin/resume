(function() {
    'use strict';
    angular
        .module('resumeApp')
        .factory('ResumeExperienceProject', ResumeExperienceProject);

    ResumeExperienceProject.$inject = ['$resource', 'DateUtils'];

    function ResumeExperienceProject ($resource, DateUtils) {
        var resourceUrl =  'api/resume-experience-projects/:id';

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
