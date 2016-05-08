'use strict';

describe('Controller Tests', function() {

    describe('Resume Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockResume, MockUser, MockResumeExperience, MockResumeSkill, MockResumePaper, MockResumeEducation;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockResume = jasmine.createSpy('MockResume');
            MockUser = jasmine.createSpy('MockUser');
            MockResumeExperience = jasmine.createSpy('MockResumeExperience');
            MockResumeSkill = jasmine.createSpy('MockResumeSkill');
            MockResumePaper = jasmine.createSpy('MockResumePaper');
            MockResumeEducation = jasmine.createSpy('MockResumeEducation');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity ,
                'Resume': MockResume,
                'User': MockUser,
                'ResumeExperience': MockResumeExperience,
                'ResumeSkill': MockResumeSkill,
                'ResumePaper': MockResumePaper,
                'ResumeEducation': MockResumeEducation
            };
            createController = function() {
                $injector.get('$controller')("ResumeDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'resumeApp:resumeUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
