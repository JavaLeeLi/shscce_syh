/**
 * 客服管理
 */
window.appModule.config(function($stateProvider) {
    //定义工作台
    $stateProvider
        .state('customerService', {
            parent: 'layout',
            url:    '/customerService'
        })
        .state('customerService.manage', {
            url:    '/manage',
            views:{
                //主文件
                'main@layout': {
                    templateUrl: 'customerService/manage/list',
                    controller: 'CustomerServiceManageController as vm'
                }
            }
        })

});
