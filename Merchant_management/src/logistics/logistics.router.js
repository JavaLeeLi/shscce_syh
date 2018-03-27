/**
 * 商品管理
 */
window.appModule.config(function($stateProvider) {
    //定义首页路由
    $stateProvider
        .state('logistics',{
            parent: 'layout',
            url: '/logistics',
            views:{
                //主文件
                'main@layout': {
                    templateUrl: 'logistics/list',
                    controller: 'LogisticsController as vm'
                }
            }
        })
});
