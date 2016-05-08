'use strict';

describe('Controller Tests', function() {

    describe('ResumeExperienceProject Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockResumeExperienceProject, MockResumeExperienceProjectAccomplish, MockResumeExperience;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockResumeExperienceProject = jasmine.createSpy('MockResumeExperienceProject');
            MockResumeExperienceProjectAccomplish = jasmine.createSpy('MockResumeExperienceProjectAccomplish');
            MockResumeExperience = jasmine.createSpy('MockResumeExperience');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity ,
                'ResumeExperienceProject': MockResumeExperienceProject,
                'ResumeExperienceProjectAccomplish': MockResumeExperienceProjectAccomplish,
                'ResumeExperience': MockResumeExperience
            };
            createController = function() {
                $injector.get('$controller')("ResumeExperienceProjectDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'resumeApp:resumeExperienceProjectUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
