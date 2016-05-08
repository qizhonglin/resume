(function() {
    'use strict';

    angular
        .module('resumeApp')
        .factory('ResumeExperienceSearch', ResumeExperienceSearch);

    ResumeExperienceSearch.$inject = ['$resource'];

    function ResumeExperienceSearch($resource) {
        var resourceUrl =  'api/_search/resume-experiences/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true}
        });
    }
})();
