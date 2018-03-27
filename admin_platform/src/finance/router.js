/**
 * 分销管理
 */
window.appModule.config(function($stateProvider) {
    $stateProvider
        .state('finance', {
            parent: 'layout',
            url:    '/finance'
        })
        .state('finance.overview', {
            url:    '/overview',
            views:{
                //主文件
                'main@layout': {
                    templateUrl: 'finance/overview/list',
                    controller: 'FinanceOverviewController as vm'
                }
            }
        })

});
