window.appModule.controller('LeftController', controllerFn);
function controllerFn($rootScope,$state,SUB_MENUS){
    var vm = this;
    vm.state = $state;

    initMenu($state.$current);

    $rootScope.$on('$stateChangeSuccess',function(e,toState){
        initMenu(toState);
    });
    function initMenu(toState){
        var stateArr = toState.name.split('.');
        vm.subMenus = SUB_MENUS[stateArr[0]];
        // angular.forEach(vm.subMenus,function(item){
        //     if(item.privilege){
        //         var flag = authFactory.isAuthorized(item.privilege);
        //         item.isShowMenu = flag;
        //     }
        // })
    }

}