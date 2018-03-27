/**
 * 分销管理
 */
window.appModule.config(function($stateProvider) {
    $stateProvider
        .state('distribution', {
            parent: 'layout',
            url:    '/distribution'
        })
        .state('distribution.goods', {
            url:    '/goods',
            views:{
                //主文件
                'main@layout': {
                    templateUrl: 'distribution/goods/list',
                    controller: 'DistributionGoodsController as vm'
                }
            }
        })

});
