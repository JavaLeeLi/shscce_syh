/**
 * 登录注册等用户相关路由
 */
window.appModule.config(function($stateProvider) {
    $stateProvider
        .state('login', {
            url:         '/login',
            templateUrl: 'session/login/login',
            controller:  'LoginController as vm'
        })


});
