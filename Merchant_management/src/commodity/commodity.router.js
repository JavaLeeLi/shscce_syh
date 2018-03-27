/**
 * 商品管理
 */
window.appModule.config(function($stateProvider) {
    //定义首页路由
    $stateProvider
        .state('commodity',{
            parent: 'layout',
            url: '/commodity',
            views:{
                //主文件
                'main@layout': {
                    templateUrl: 'commodity/list',
                    controller: 'CommodityController as vm'
                }
            }
        })
});
