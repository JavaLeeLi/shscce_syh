/**
 * Created by liuss on 16/3/16.
 */
window.appModule.directive('vsVideo', directiveFn);

function directiveFn(){

  videojs.options.flash.swf = "assets/media/video-js.swf";

  return {
    restrict : 'E',
    replace: true,
    template: '<video class="video-js vjs-default-skin vjs-live-control vjs-time-control vjs-big-play-centered m-v-10" controls style="margin:0 auto;min-width: 400px;"></video>',
    scope : {
      videoData : '='
    },
    link : function(scope,tElement){
      _.forEach(scope.videoData.source,function(source){
        $('<source>').attr('src',source.src).attr('type',source.type).appendTo(tElement);
      });

      if(scope.videoData.warning) {
        tElement.append($('<p class="vjs-no-js"></p>').html(scope.videoData.warning));
      }

      _.forEach(scope.videoData.config,function(value,key){
        tElement.attr(key,value);
      });

      videojs(tElement[0]);
    }
  }
}
