/**
 * 店铺管理
 */
window.appModule.config(function($stateProvider) {
    //定义工作台
    $stateProvider
        .state('shop', {
            parent: 'layout',
            url:    '/shop'
        })
        .state('shop.manage', {
            url:    '/manage',
            views:{
                //主文件
                'main@layout': {
                    templateUrl: 'shop/manage/list',
                    controller: 'ShopManageController as vm'
                }
            }
        })
        .state('shop.service', {
            url:    '/service',
            views:{
                //主文件
                'main@layout': {
                    templateUrl: 'shop/service/list',
                    controller: 'ShopServiceController as vm'
                }
            }
        })
        .state('shop.marketing', {
            url:    '/marketing',
            views:{
                //主文件
                'main@layout': {
                    templateUrl: 'shop/marketing/list',
                    controller: 'ShopMarketingController as vm'
                }
            }
        })

});
