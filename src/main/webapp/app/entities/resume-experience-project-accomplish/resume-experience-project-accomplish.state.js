(function() {
    'use strict';

    angular
        .module('resumeApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('resume-experience-project-accomplish', {
            parent: 'entity',
            url: '/resume-experience-project-accomplish',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'resumeApp.resumeExperienceProjectAccomplish.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/resume-experience-project-accomplish/resume-experience-project-accomplishes.html',
                    controller: 'ResumeExperienceProjectAccomplishController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('resumeExperienceProjectAccomplish');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('resume-experience-project-accomplish-detail', {
            parent: 'entity',
            url: '/resume-experience-project-accomplish/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'resumeApp.resumeExperienceProjectAccomplish.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/resume-experience-project-accomplish/resume-experience-project-accomplish-detail.html',
                    controller: 'ResumeExperienceProjectAccomplishDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('resumeExperienceProjectAccomplish');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'ResumeExperienceProjectAccomplish', function($stateParams, ResumeExperienceProjectAccomplish) {
                    return ResumeExperienceProjectAccomplish.get({id : $stateParams.id});
                }]
            }
        })
        .state('resume-experience-project-accomplish.new', {
            parent: 'resume-experience-project-accomplish',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/resume-experience-project-accomplish/resume-experience-project-accomplish-dialog.html',
                    controller: 'ResumeExperienceProjectAccomplishDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                accomplish: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('resume-experience-project-accomplish', null, { reload: true });
                }, function() {
                    $state.go('resume-experience-project-accomplish');
                });
            }]
        })
        .state('resume-experience-project-accomplish.edit', {
            parent: 'resume-experience-project-accomplish',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/resume-experience-project-accomplish/resume-experience-project-accomplish-dialog.html',
                    controller: 'ResumeExperienceProjectAccomplishDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['ResumeExperienceProjectAccomplish', function(ResumeExperienceProjectAccomplish) {
                            return ResumeExperienceProjectAccomplish.get({id : $stateParams.id});
                        }]
                    }
                }).result.then(function() {
                    $state.go('resume-experience-project-accomplish', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('resume-experience-project-accomplish.delete', {
            parent: 'resume-experience-project-accomplish',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/resume-experience-project-accomplish/resume-experience-project-accomplish-delete-dialog.html',
                    controller: 'ResumeExperienceProjectAccomplishDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['ResumeExperienceProjectAccomplish', function(ResumeExperienceProjectAccomplish) {
                            return ResumeExperienceProjectAccomplish.get({id : $stateParams.id});
                        }]
                    }
                }).result.then(function() {
                    $state.go('resume-experience-project-accomplish', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
