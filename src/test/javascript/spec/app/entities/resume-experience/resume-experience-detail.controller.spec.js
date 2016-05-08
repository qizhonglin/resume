'use strict';

describe('Controller Tests', function() {

    describe('ResumeExperience Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockResumeExperience, MockResumeExperienceProject, MockResume;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockResumeExperience = jasmine.createSpy('MockResumeExperience');
            MockResumeExperienceProject = jasmine.createSpy('MockResumeExperienceProject');
            MockResume = jasmine.createSpy('MockResume');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity ,
                'ResumeExperience': MockResumeExperience,
                'ResumeExperienceProject': MockResumeExperienceProject,
                'Resume': MockResume
            };
            createController = function() {
                $injector.get('$controller')("ResumeExperienceDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'resumeApp:resumeExperienceUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
