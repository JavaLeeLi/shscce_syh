/**
 * 交易管理
 */
window.appModule.config(function($stateProvider) {
    //定义工作台
    $stateProvider
        .state('transaction',{
            parent: 'layout',
            url: '/transaction'
        })
        //订单
        .state('transaction.order', {
            url:    '/order',
            views:{
                //主文件
                'main@layout': {
                    templateUrl: 'transaction/order/list',
                    controller: 'OrderController as vm'
                }
            }
        })
        //退货
        .state('transaction.return', {
            url:   '/return',
            views:{
                //主文件
                'main@layout': {
                    templateUrl: 'transaction/return/list',
                    controller: 'ReturnController as vm'
                }
            }
        })
        //退款
        .state('transaction.refunds', {
            url:   '/refunds',
            views:{
                //主文件
                'main@layout': {
                    templateUrl: 'transaction/refunds/list',
                    controller: 'RefundsController as vm'
                }
            }
        })
});
