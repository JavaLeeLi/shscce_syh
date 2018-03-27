window.appModule.directive('vsHeaderTop',directiveFn);

function directiveFn() {
    return {
        link: function(scope, element, attrs) {
            //*****************************************************************
            var st;
            element.attr('otop', element.offset().top); //存储原来的距离顶部的距离
            $(window).scroll(function () {
                st = Math.max(document.body.scrollTop || document.documentElement.scrollTop);
                if (st > parseInt(element.attr('otop'))) {
                    element.css({
                        'position': 'fixed',
                        'top':      0,
                        'left':     0,
                        'z-index':  10000,
                        'opacity':  1,
                        'height':   '40px'
                    });
                }
                else {
                        element.css({'position': 'static'});
                    }
            })
        }
    }
}
