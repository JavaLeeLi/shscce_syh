window.appModule.controller('ShopServiceController',controllerFn);

function controllerFn($uibModal) {
    var vm = this;

    vm.services = [
        { name:'优惠券',status:'正常使用',cost:'10'}
    ]

    vm.editCost = editCostFn;
    
    function editCostFn(item) {
        item.isEdit = true;
        item.tmp_cost = item.cost;
    }
}


window.appModule.controller('editCostController',subControllerfn);
function subControllerfn($uibModalInstance,service_term) {
    var $ctrl = this;

    $ctrl.service = service_term;

    $ctrl.ok = function () {
        //TODO  调用修改密码的接口，返回父控制器成功失败标识
        $uibModalInstance.close(flag);
    };

    $ctrl.cancel = function () {
        $uibModalInstance.dismiss('cancel');
    };
}