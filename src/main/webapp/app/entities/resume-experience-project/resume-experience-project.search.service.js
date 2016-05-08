(function() {
    'use strict';

    angular
        .module('resumeApp')
        .factory('ResumeExperienceProjectSearch', ResumeExperienceProjectSearch);

    ResumeExperienceProjectSearch.$inject = ['$resource'];

    function ResumeExperienceProjectSearch($resource) {
        var resourceUrl =  'api/_search/resume-experience-projects/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true}
        });
    }
})();
