/**
 * Created by liuss on 16/3/16.
 */
window.appModule.directive('vsCropper', directiveFn);

function directiveFn($timeout){

  return {
    replace: true,
    templateUrl: 'common/component/vs-cropper/vs-cropper',
    scope : {
      sourceSrc : '=',
      crop: '='
    },
    link : function(scope,tElement){
      var instance;

      var delayAssign = delayAssignFn();

      scope.$watch('sourceSrc', function (newVal, oldVal) {
        if(newVal && !instance){
          instance = tElement.find('.source img').attr('src',newVal);
          createInstance(instance,delayAssign);
        }else if(newVal){
          instance.cropper('replace',newVal);
        }
      });

      function createInstance(ele,cropFn){
        ele.cropper({
          viewMode: 1,
          preview: '.cm_cropper .preview',
          aspectRatio: 16/16,
          crop: function(e) {
            var event = e;
            cropFn(function () {
              scope.$apply(function () {
                if(!scope.crop) scope.crop = {};
                scope.crop.width = event.width;
                scope.crop.height = event.height;
                scope.crop.x = event.x;
                scope.crop.y = event.y;
              });
            });
          }
        });
      }
    }
  };



  function delayAssignFn(){
    var timeout;

    return function (fn){
      timeout && $timeout.cancel(timeout);
      timeout = $timeout(fn,300);
    }
  }
}
