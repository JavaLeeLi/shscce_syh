window.appModule.controller('DealOrderController',controllerFn);

function controllerFn() {
    var vm = this;

    vm.data = [
        {
            order_no:'1231231231',
            time:'2017-08-09',
            name:'三只松鼠',
            pay_method:'在线支付'
        },{
            order_no:'1231231231',
            time:'2017-08-09',
            name:'三只松鼠',
            pay_method:'在线支付'
        }
    ];

    vm.switch = switchFn;

    function switchFn(tabName){
        vm.tab = tabName;
    }

}