window.appModule.controller('ShopManageController',controllerFn);

function controllerFn() {
    var vm = this;

    vm.switch = switchFn;

    function switchFn(tabName){
        vm.tab = tabName;
    }

}