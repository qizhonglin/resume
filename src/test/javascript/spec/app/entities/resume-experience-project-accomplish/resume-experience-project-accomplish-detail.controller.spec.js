'use strict';

describe('Controller Tests', function() {

    describe('ResumeExperienceProjectAccomplish Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockResumeExperienceProjectAccomplish, MockResumeExperienceProject;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockResumeExperienceProjectAccomplish = jasmine.createSpy('MockResumeExperienceProjectAccomplish');
            MockResumeExperienceProject = jasmine.createSpy('MockResumeExperienceProject');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity ,
                'ResumeExperienceProjectAccomplish': MockResumeExperienceProjectAccomplish,
                'ResumeExperienceProject': MockResumeExperienceProject
            };
            createController = function() {
                $injector.get('$controller')("ResumeExperienceProjectAccomplishDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'resumeApp:resumeExperienceProjectAccomplishUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
