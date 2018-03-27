window.appModule.directive('vsSlide',directiveFn);

function directiveFn() {
    return {
        restrict : 'A',
        scope : {
            vsSlide : '='
        },
        link: function(scope, element, attrs) {
            var params = scope.vsSlide || {};
            var Eid = element[0].id;
            var slide = element[0].id;
            var options = {
                container:document.getElementById(Eid), //容器
                "width":params.width || '', //宽度
                "height":params.height || 240, //高度
                "speed":params.speed || 500, //速度
                "autoPlay": params.autoPlay || 3000, //自动播放
                "loop":params.loop || true,//是否循环
                "pageShow":params.pageShow || true,//是否显示分页器
                "pageStyle":params.pageStyle || 'dot', //分页器样式，分dot,line
                'dotPosition':params.dotPosition || 'center' //当分页器样式为dot时控制分页器位置，left,center,right
            };
            slide = new auiSlide(options);
        }
    }
}
