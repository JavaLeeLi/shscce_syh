window.appModule.controller('CustomerServiceManageController',controllerFn);

function controllerFn() {
    var vm = this;

    vm.customers = [
        {
            qq:'2123',
            type:'22',
            name:'客服1',
            category:'platform'
        },
        {
            qq:'1111',
            type:'333',
            name:'客服2',
            category:'consult'
        }
    ];

}