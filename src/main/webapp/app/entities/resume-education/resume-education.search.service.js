(function() {
    'use strict';

    angular
        .module('resumeApp')
        .factory('ResumeEducationSearch', ResumeEducationSearch);

    ResumeEducationSearch.$inject = ['$resource'];

    function ResumeEducationSearch($resource) {
        var resourceUrl =  'api/_search/resume-educations/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true}
        });
    }
})();
