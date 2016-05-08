(function() {
    'use strict';

    angular
        .module('resumeApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('resume-experience-project', {
            parent: 'entity',
            url: '/resume-experience-project',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'resumeApp.resumeExperienceProject.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/resume-experience-project/resume-experience-projects.html',
                    controller: 'ResumeExperienceProjectController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('resumeExperienceProject');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('resume-experience-project-detail', {
            parent: 'entity',
            url: '/resume-experience-project/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'resumeApp.resumeExperienceProject.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/resume-experience-project/resume-experience-project-detail.html',
                    controller: 'ResumeExperienceProjectDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('resumeExperienceProject');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'ResumeExperienceProject', function($stateParams, ResumeExperienceProject) {
                    return ResumeExperienceProject.get({id : $stateParams.id});
                }]
            }
        })
        .state('resume-experience-project.new', {
            parent: 'resume-experience-project',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/resume-experience-project/resume-experience-project-dialog.html',
                    controller: 'ResumeExperienceProjectDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                index: null,
                                name: null,
                                startTime: null,
                                endTime: null,
                                introduction: null,
                                responsiility: null,
                                platform: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('resume-experience-project', null, { reload: true });
                }, function() {
                    $state.go('resume-experience-project');
                });
            }]
        })
        .state('resume-experience-project.edit', {
            parent: 'resume-experience-project',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/resume-experience-project/resume-experience-project-dialog.html',
                    controller: 'ResumeExperienceProjectDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['ResumeExperienceProject', function(ResumeExperienceProject) {
                            return ResumeExperienceProject.get({id : $stateParams.id});
                        }]
                    }
                }).result.then(function() {
                    $state.go('resume-experience-project', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('resume-experience-project.delete', {
            parent: 'resume-experience-project',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/resume-experience-project/resume-experience-project-delete-dialog.html',
                    controller: 'ResumeExperienceProjectDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['ResumeExperienceProject', function(ResumeExperienceProject) {
                            return ResumeExperienceProject.get({id : $stateParams.id});
                        }]
                    }
                }).result.then(function() {
                    $state.go('resume-experience-project', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
