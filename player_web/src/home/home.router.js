/**
 * 首页路由
 */
window.appModule.config(function($stateProvider) {
    //定义首页路由
    $stateProvider
        .state('home',{
            parent: 'layout',
            url: '/home',
            views:{
                //主文件
                'main@layout': {
                    templateUrl: 'home/index',
                    controller: 'HomeController as vm'
                }
            }
        })
});
