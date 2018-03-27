
window.appModule.controller('VipIntegralController',controllerFn);

function controllerFn($uibModal) {
    var vm = this;
    vm.manageIntegral = manageIntegralFn;
    
    function manageIntegralFn() {
        var modalInstance = $uibModal.open({
            templateUrl: 'integral-management.html',
            controller: 'IntegralManagementController',
            controllerAs: '$ctrl'
        });
        modalInstance.result.then(function (res) {
            vm.flag = res;
        }, function () {
            console.log('弹出框关闭');
        });
    }
}
window.appModule.controller('IntegralManagementController',subControllerfn);
function subControllerfn($uibModalInstance) {
    var $ctrl = this;

    $ctrl.ok = function () {
        //TODO  调用修改密码的接口，返回父控制器成功失败标识
        $uibModalInstance.close(flag);
    };

    $ctrl.cancel = function () {
        $uibModalInstance.dismiss('cancel');
    };
}

