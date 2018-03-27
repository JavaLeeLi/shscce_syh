window.appModule.factory('bodyUtil',factoryFn);

function factoryFn($q,$injector){

  var _body = angular.element(document.body);

  return {
    addClass: _.bind(_body.addClass,_body),
    removeClass : _.bind(_body.removeClass,_body),
    toggleClass : _.bind(_body.toggleClass,_body),
    hasClass : _.bind(_body.hasClass,_body),
    clearAndAddClass: clearAndAddClassFn
  }

  /************************************************************/


  function clearAndAddClassFn(className){
    _body.removeClass().addClass(className);
  }
}


