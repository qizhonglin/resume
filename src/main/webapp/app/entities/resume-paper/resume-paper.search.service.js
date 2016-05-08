(function() {
    'use strict';

    angular
        .module('resumeApp')
        .factory('ResumePaperSearch', ResumePaperSearch);

    ResumePaperSearch.$inject = ['$resource'];

    function ResumePaperSearch($resource) {
        var resourceUrl =  'api/_search/resume-papers/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true}
        });
    }
})();
