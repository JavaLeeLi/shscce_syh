window.appModule.directive('vnNavbarSubmenu',directiveFn);

function directiveFn($templateCache, $compile, $state) {
    return {
        restrict: 'A',
        scope: {
            menus: '=vnNavbarSubmenu',
            showOn: '@',
            hide: '='
        },
        link: vnNavbarSubmenuLink
    };

    function vnNavbarSubmenuLink(scope, element) {
        var $element = angular.element(element);
        var template = $compile($templateCache.get(
                'common/component/vn-navbar-submenu/template'
            ))(scope);
        var $link = $element.find('a').after(template);

        $link.on('mouseenter', function(event) {
            angular.element(
                document.querySelectorAll('.nav-submenu')
            ).removeClass('show');

            angular.element(event.currentTarget)
                .parent().find('ul').addClass('show');
        });

        $element.on('mouseleave', function() {
            angular.element(
                document.querySelectorAll('.nav-submenu')
            ).removeClass('show');

            angular.element(
                document.querySelectorAll('.current')
            ).addClass('show');
        });

        scope.$state = $state;
    }
}
