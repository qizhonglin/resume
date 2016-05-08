(function() {
    'use strict';

    angular
        .module('resumeApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('resume-experience', {
            parent: 'entity',
            url: '/resume-experience',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'resumeApp.resumeExperience.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/resume-experience/resume-experiences.html',
                    controller: 'ResumeExperienceController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('resumeExperience');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('resume-experience-detail', {
            parent: 'entity',
            url: '/resume-experience/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'resumeApp.resumeExperience.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/resume-experience/resume-experience-detail.html',
                    controller: 'ResumeExperienceDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('resumeExperience');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'ResumeExperience', function($stateParams, ResumeExperience) {
                    return ResumeExperience.get({id : $stateParams.id});
                }]
            }
        })
        .state('resume-experience.new', {
            parent: 'resume-experience',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/resume-experience/resume-experience-dialog.html',
                    controller: 'ResumeExperienceDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                index: null,
                                position: null,
                                company: null,
                                startTime: null,
                                endTime: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('resume-experience', null, { reload: true });
                }, function() {
                    $state.go('resume-experience');
                });
            }]
        })
        .state('resume-experience.edit', {
            parent: 'resume-experience',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/resume-experience/resume-experience-dialog.html',
                    controller: 'ResumeExperienceDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['ResumeExperience', function(ResumeExperience) {
                            return ResumeExperience.get({id : $stateParams.id});
                        }]
                    }
                }).result.then(function() {
                    $state.go('resume-experience', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('resume-experience.delete', {
            parent: 'resume-experience',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/resume-experience/resume-experience-delete-dialog.html',
                    controller: 'ResumeExperienceDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['ResumeExperience', function(ResumeExperience) {
                            return ResumeExperience.get({id : $stateParams.id});
                        }]
                    }
                }).result.then(function() {
                    $state.go('resume-experience', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
