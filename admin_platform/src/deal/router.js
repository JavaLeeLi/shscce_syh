/**
 * 交易管理
 */
window.appModule.config(function($stateProvider) {
    //定义工作台
    $stateProvider
        .state('deal', {
            parent: 'layout',
            url:    '/deal'
        })
        .state('deal.order', {
            url:    '/order',
            views:{
                //主文件
                'main@layout': {
                    templateUrl: 'deal/order/list',
                    controller: 'DealOrderController as vm'
                }
            }
        })
        .state('deal.dispute', {
            url:    '/dispute',
            views:{
                //主文件
                'main@layout': {
                    templateUrl: 'deal/dispute/list',
                    controller: 'DealDisputeController as vm'
                }
            }
        })
        .state('deal.payment', {
            url:    '/payment',
            views:{
                //主文件
                'main@layout': {
                    templateUrl: 'deal/payment/list',
                    controller: 'DealPaymentController as vm'
                }
            }
        })

});
