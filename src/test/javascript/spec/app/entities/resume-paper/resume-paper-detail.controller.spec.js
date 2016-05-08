'use strict';

describe('Controller Tests', function() {

    describe('ResumePaper Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockResumePaper, MockResume;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockResumePaper = jasmine.createSpy('MockResumePaper');
            MockResume = jasmine.createSpy('MockResume');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity ,
                'ResumePaper': MockResumePaper,
                'Resume': MockResume
            };
            createController = function() {
                $injector.get('$controller')("ResumePaperDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'resumeApp:resumePaperUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
