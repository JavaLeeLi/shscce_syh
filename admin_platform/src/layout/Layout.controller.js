
window.appModule.controller('Layout', controllerFn);

function controllerFn($rootScope, $state) {
    var vm = this;

    init();

    /***********************************************************************/

    function init() {
        vm.state = $state;

        $rootScope.$on('$stateChangeSuccess', function(e, toState, toParams) {


        });
    }

    vm.open = function() {
        alert()
    }
}
