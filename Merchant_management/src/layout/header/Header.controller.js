
window.appModule.controller('HeaderController', controllerFn);

function controllerFn($state,SUB_MENUS,$filter,$stateParams) {

    var header = this;
    header.state = $state;
    header.subMenus = SUB_MENUS;

}
