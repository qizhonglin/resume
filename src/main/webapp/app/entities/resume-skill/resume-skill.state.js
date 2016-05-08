(function() {
    'use strict';

    angular
        .module('resumeApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('resume-skill', {
            parent: 'entity',
            url: '/resume-skill',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'resumeApp.resumeSkill.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/resume-skill/resume-skills.html',
                    controller: 'ResumeSkillController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('resumeSkill');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('resume-skill-detail', {
            parent: 'entity',
            url: '/resume-skill/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'resumeApp.resumeSkill.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/resume-skill/resume-skill-detail.html',
                    controller: 'ResumeSkillDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('resumeSkill');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'ResumeSkill', function($stateParams, ResumeSkill) {
                    return ResumeSkill.get({id : $stateParams.id});
                }]
            }
        })
        .state('resume-skill.new', {
            parent: 'resume-skill',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/resume-skill/resume-skill-dialog.html',
                    controller: 'ResumeSkillDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                skill: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('resume-skill', null, { reload: true });
                }, function() {
                    $state.go('resume-skill');
                });
            }]
        })
        .state('resume-skill.edit', {
            parent: 'resume-skill',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/resume-skill/resume-skill-dialog.html',
                    controller: 'ResumeSkillDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['ResumeSkill', function(ResumeSkill) {
                            return ResumeSkill.get({id : $stateParams.id});
                        }]
                    }
                }).result.then(function() {
                    $state.go('resume-skill', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('resume-skill.delete', {
            parent: 'resume-skill',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/resume-skill/resume-skill-delete-dialog.html',
                    controller: 'ResumeSkillDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['ResumeSkill', function(ResumeSkill) {
                            return ResumeSkill.get({id : $stateParams.id});
                        }]
                    }
                }).result.then(function() {
                    $state.go('resume-skill', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
