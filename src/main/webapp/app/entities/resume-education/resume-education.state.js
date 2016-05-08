(function() {
    'use strict';

    angular
        .module('resumeApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('resume-education', {
            parent: 'entity',
            url: '/resume-education',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'resumeApp.resumeEducation.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/resume-education/resume-educations.html',
                    controller: 'ResumeEducationController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('resumeEducation');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('resume-education-detail', {
            parent: 'entity',
            url: '/resume-education/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'resumeApp.resumeEducation.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/resume-education/resume-education-detail.html',
                    controller: 'ResumeEducationDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('resumeEducation');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'ResumeEducation', function($stateParams, ResumeEducation) {
                    return ResumeEducation.get({id : $stateParams.id});
                }]
            }
        })
        .state('resume-education.new', {
            parent: 'resume-education',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/resume-education/resume-education-dialog.html',
                    controller: 'ResumeEducationDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                major: null,
                                university: null,
                                startTime: null,
                                endTime: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('resume-education', null, { reload: true });
                }, function() {
                    $state.go('resume-education');
                });
            }]
        })
        .state('resume-education.edit', {
            parent: 'resume-education',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/resume-education/resume-education-dialog.html',
                    controller: 'ResumeEducationDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['ResumeEducation', function(ResumeEducation) {
                            return ResumeEducation.get({id : $stateParams.id});
                        }]
                    }
                }).result.then(function() {
                    $state.go('resume-education', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('resume-education.delete', {
            parent: 'resume-education',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/resume-education/resume-education-delete-dialog.html',
                    controller: 'ResumeEducationDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['ResumeEducation', function(ResumeEducation) {
                            return ResumeEducation.get({id : $stateParams.id});
                        }]
                    }
                }).result.then(function() {
                    $state.go('resume-education', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
