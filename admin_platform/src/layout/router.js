/**
 * Created by liuss on 16/2/1.
 */
window.appModule.config(function($stateProvider) {
    //定义首页路由
    $stateProvider
        .state('layout', {
            abstract: true,
            cache:      'false',
            params:{
                search : ""
            },
            views:    {
                //主文件
                '':              {
                    templateUrl: 'layout/layout',
                    controller:  'Layout as vm'
                },
                //左侧文件
                'left@layout': {
                    templateUrl: 'layout/side/left',
                    controller:  'LeftController as vm'
                },
                //头部文件
                'header@layout': {
                    templateUrl: 'layout/header/header',
                    controller:  'HeaderController as vm'
                },
                //底部文件
                'footer@layout': {
                    templateUrl: 'layout/footer/footer'
                }
            }
        })
});
