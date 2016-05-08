(function() {
    'use strict';

    angular
        .module('resumeApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('resume', {
            parent: 'entity',
            url: '/resume',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'resumeApp.resume.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/resume/resumes.html',
                    controller: 'ResumeController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('resume');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('resume-detail', {
            parent: 'entity',
            url: '/resume/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'resumeApp.resume.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/resume/resume-detail.html',
                    controller: 'ResumeDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('resume');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'Resume', function($stateParams, Resume) {
                    return Resume.get({id : $stateParams.id});
                }]
            }
        })
        .state('resume.new', {
            parent: 'resume',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/resume/resume-dialog.html',
                    controller: 'ResumeDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                infoEmail: null,
                                infoPhone: null,
                                infoGithub: null,
                                infoLinkedin: null,
                                profileBasic: null,
                                profileTechniqueDomain: null,
                                profileSoftwareSystem: null,
                                profileMultibranchExperience: null,
                                profilePreferredPosition: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('resume', null, { reload: true });
                }, function() {
                    $state.go('resume');
                });
            }]
        })
        .state('resume.edit', {
            parent: 'resume',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/resume/resume-dialog.html',
                    controller: 'ResumeDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Resume', function(Resume) {
                            return Resume.get({id : $stateParams.id});
                        }]
                    }
                }).result.then(function() {
                    $state.go('resume', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('resume.delete', {
            parent: 'resume',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/resume/resume-delete-dialog.html',
                    controller: 'ResumeDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Resume', function(Resume) {
                            return Resume.get({id : $stateParams.id});
                        }]
                    }
                }).result.then(function() {
                    $state.go('resume', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
