'use strict';

describe('Controller Tests', function() {

    describe('ResumeEducation Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockResumeEducation, MockResume;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockResumeEducation = jasmine.createSpy('MockResumeEducation');
            MockResume = jasmine.createSpy('MockResume');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity ,
                'ResumeEducation': MockResumeEducation,
                'Resume': MockResume
            };
            createController = function() {
                $injector.get('$controller')("ResumeEducationDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'resumeApp:resumeEducationUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
