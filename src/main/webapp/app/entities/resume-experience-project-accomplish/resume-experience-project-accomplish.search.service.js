(function() {
    'use strict';

    angular
        .module('resumeApp')
        .factory('ResumeExperienceProjectAccomplishSearch', ResumeExperienceProjectAccomplishSearch);

    ResumeExperienceProjectAccomplishSearch.$inject = ['$resource'];

    function ResumeExperienceProjectAccomplishSearch($resource) {
        var resourceUrl =  'api/_search/resume-experience-project-accomplishes/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true}
        });
    }
})();
