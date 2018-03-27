window.appModule.controller('VipManageController',controllerFn);

function controllerFn($uibModal) {
    var vm = this;

    vm.updatePassword = updatePasswordFn;
    vm.editTag = editTagFn;
    function updatePasswordFn(item) {
        var modalInstance = $uibModal.open({
            templateUrl: 'updatePassword.html',
            controller: 'UpdatePasswordController',
            controllerAs: '$ctrl'
        });
        modalInstance.result.then(function (res) {
            vm.flag = res;
        }, function () {
            console.log('弹出框关闭');
        });
    }
    function editTagFn(){
        var modalInstance = $uibModal.open({
            templateUrl: 'updatePassword.html',
            controller: 'UpdatePasswordController',
            controllerAs: '$ctrl'
        });
        modalInstance.result.then(function (res) {
            vm.flag = res;
        }, function () {
            console.log('弹出框关闭');
        });
    }
}

window.appModule.controller('UpdatePasswordController',subControllerfn);
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