window.appModule.directive('vsGoTop',directiveFn);

function directiveFn() {
  return {
    link: function(scope, element, attrs) {
      var offset = attrs.offset || 300;
      var duration = attrs.duration || 500;
      element.hide();

      if (navigator.userAgent.match(/iPhone|iPad|iPod/i)) {  // ios supported
        $(window).bind("touchend touchcancel touchleave", function(e){
          if ($(this).scrollTop() > offset) {
            element.fadeIn(duration);
          } else {
            element.fadeOut(duration);
          }
        });
      } else {  // general
        $(window).scroll(function() {
          if ($(this).scrollTop() > offset) {
            element.fadeIn(duration);
          } else {
            element.fadeOut(duration);
          }
        });
      }

      element.click(function(e) {
        e.preventDefault();
        $('html, body').animate({scrollTop: 0}, duration);
        return false;
      });
    }
  };
}
