/**
 * 商品管理
 */
window.appModule.config(function($stateProvider) {
    //定义工作台
    $stateProvider
        .state('goods', {
            parent: 'layout',
            url:    '/goods'
        })
        .state('goods.manage', {
            url:    '/manage',
            views:{
                //主文件
                'main@layout': {
                    templateUrl: 'goods/manage/list',
                    controller: 'GoodsManageController as vm'
                }
            }
        })
        .state('goods.class', {
            url:   '/class',
            views:{
                //主文件
                'main@layout': {
                    templateUrl: 'goods/class/list',
                    controller: 'GoodsClassController as vm'
                }
            }
        })
        .state('goods.class.add', {
            url:   '/add',
            views:{
                //主文件
                'main@layout': {
                    templateUrl: 'goods/class/add',
                    controller: 'GoodsClassAddController as vm'
                }
            }
        })

});
