(function() {
    'use strict';

    angular
        .module('resumeApp')
        .factory('ResumeSkillSearch', ResumeSkillSearch);

    ResumeSkillSearch.$inject = ['$resource'];

    function ResumeSkillSearch($resource) {
        var resourceUrl =  'api/_search/resume-skills/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true}
        });
    }
})();
