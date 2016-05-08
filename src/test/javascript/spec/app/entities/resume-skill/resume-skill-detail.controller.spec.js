'use strict';

describe('Controller Tests', function() {

    describe('ResumeSkill Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockResumeSkill, MockResume;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockResumeSkill = jasmine.createSpy('MockResumeSkill');
            MockResume = jasmine.createSpy('MockResume');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity ,
                'ResumeSkill': MockResumeSkill,
                'Resume': MockResume
            };
            createController = function() {
                $injector.get('$controller')("ResumeSkillDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'resumeApp:resumeSkillUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
