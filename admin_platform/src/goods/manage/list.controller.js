window.appModule.controller('GoodsManageController',controllerFn);

function controllerFn() {
    var vm = this;
    vm.class = [
        {id:'1',name:'aa'},
        {id:'1',name:'aa'},
        {id:'1',name:'aa'},
        {id:'1',name:'aa'},
    ];

    vm.switch = switchFn;

    function switchFn(tabName) {
        vm.tab = tabName;
    }
}