(function() {
    'use strict';

    angular
        .module('resumeApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('resume-paper', {
            parent: 'entity',
            url: '/resume-paper',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'resumeApp.resumePaper.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/resume-paper/resume-papers.html',
                    controller: 'ResumePaperController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('resumePaper');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('resume-paper-detail', {
            parent: 'entity',
            url: '/resume-paper/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'resumeApp.resumePaper.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/resume-paper/resume-paper-detail.html',
                    controller: 'ResumePaperDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('resumePaper');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'ResumePaper', function($stateParams, ResumePaper) {
                    return ResumePaper.get({id : $stateParams.id});
                }]
            }
        })
        .state('resume-paper.new', {
            parent: 'resume-paper',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/resume-paper/resume-paper-dialog.html',
                    controller: 'ResumePaperDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                paper: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('resume-paper', null, { reload: true });
                }, function() {
                    $state.go('resume-paper');
                });
            }]
        })
        .state('resume-paper.edit', {
            parent: 'resume-paper',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/resume-paper/resume-paper-dialog.html',
                    controller: 'ResumePaperDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['ResumePaper', function(ResumePaper) {
                            return ResumePaper.get({id : $stateParams.id});
                        }]
                    }
                }).result.then(function() {
                    $state.go('resume-paper', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('resume-paper.delete', {
            parent: 'resume-paper',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/resume-paper/resume-paper-delete-dialog.html',
                    controller: 'ResumePaperDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['ResumePaper', function(ResumePaper) {
                            return ResumePaper.get({id : $stateParams.id});
                        }]
                    }
                }).result.then(function() {
                    $state.go('resume-paper', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
