
window.appModule.controller('HeaderController', controllerFn);

function controllerFn($state,SUB_MENUS,$rootScope,$stateParams) {
    var vm = this;
    vm.state = $state;
    vm.subMenus = SUB_MENUS;

    // vm.name = vm.state.current.name.split('-')[0];
    //
    // initMenu($state.$current);
    //
    // $rootScope.$on('$stateChangeSuccess',function(e,toState){
    //     initMenu(toState);
    // });
    //
    // function initMenu(toState) {
    //     vm.name = toState.name.split('-')[0];
    // }

}
