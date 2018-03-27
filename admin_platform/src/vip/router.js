/**
 * 会员管理
 */
window.appModule.config(function($stateProvider) {
    //定义工作台
    $stateProvider
        .state('vip', {
            parent: 'layout',
            url:    '/vip'
        })
        .state('vip.manage', {
            url:    '/manage',
            views:{
                //主文件
                'main@layout': {
                    templateUrl: 'vip/manage/list',
                    controller: 'VipManageController as vm'
                }
            }
        })
        .state('vip.tag', {
            url:    '/tag',
            views:{
                //主文件
                'main@layout': {
                    templateUrl: 'vip/tag/list',
                    controller: 'VipTagController as vm'
                }
            }
        })
        .state('vip.tag.add', {
            url:    '/add',
            views:{
                //主文件
                'main@layout': {
                    templateUrl: 'vip/tag/add',
                    controller: 'VipTagAddController as vm'
                }
            }
        })
        .state('vip.integral', {
            url:    '/integral',
            views:{
                //主文件
                'main@layout': {
                    templateUrl: 'vip/integral/list',
                    controller: 'VipIntegralController as vm'
                }
            }
        })
        .state('vip.rule', {
            url:    '/rule',
            views:{
                //主文件
                'main@layout': {
                    templateUrl: 'vip/rule/list',
                    controller: 'VipRuleController as vm'
                }
            }
        })

});
